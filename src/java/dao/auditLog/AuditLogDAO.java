package dao.auditLog;

import dao.connect.DBConnect;
import dao.user.UserDAO;
import model.AuditLog;
import model.ActionType;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {

    private static final String COL_ID = "id";
    private static final String COL_TABLE_NAME = "table_name";
    private static final String COL_RECORD_ID = "record_id";
    private static final String COL_TYPE = "action_type";
    private static final String COL_MESSAGE = "message";
    private static final String COL_CHANGE_BY = "changed_by";
    private static final String COL_CHANGE_AT = "changed_at";

    private static final String BASE_SQL = """
        SELECT id, table_name, record_id, action_type,
               message, changed_by, changed_at
        FROM AuditLog
        WHERE 1=1
    """;

    public List<String> getAllTableName() throws SQLException {
        String sql = "SELECT DISTINCT table_name FROM AuditLog;";
        List<String> list = new ArrayList<>();
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("table_name"));
                }
            }

        }
        return list;
    }
    //filter+phân trang
    public List<AuditLog> getFilteredAuditLogsPaging(String tableName, String actionType, String changedBy,
        String fromDate, String toDate, int offset, int limit) throws SQLException {

    List<AuditLog> logs = new ArrayList<>();
    StringBuilder sql = new StringBuilder(BASE_SQL);
    List<Object> params = new ArrayList<>();

    if (tableName != null && !tableName.isEmpty()) {
        sql.append(" AND table_name = ?");
        params.add(tableName);
    }
    if (actionType != null && !actionType.isEmpty()) {
        sql.append(" AND action_type = ?");
        params.add(actionType);
    }
    if (changedBy != null && !changedBy.isEmpty()) {
        sql.append(" AND changed_by = ?");
        params.add(Integer.parseInt(changedBy));
    }
    if (fromDate != null && !fromDate.isEmpty()) {
        sql.append(" AND changed_at >= ?");
        params.add(fromDate + " 00:00:00");
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql.append(" AND changed_at <= ?");
        params.add(toDate + " 23:59:59");
    }

    sql.append(" ORDER BY changed_at DESC LIMIT ? OFFSET ?");
    params.add(limit);
    params.add(offset);

    try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        UserDAO userDAO = new UserDAO();

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int userId = rs.getInt(COL_CHANGE_BY);
                User user = userDAO.getUserById(userId);

                AuditLog log = new AuditLog(
                        rs.getInt(COL_ID),
                        rs.getString(COL_TABLE_NAME),
                        rs.getInt(COL_RECORD_ID),
                        ActionType.valueOf(rs.getString(COL_TYPE)),
                        rs.getString(COL_MESSAGE),
                        user,
                        rs.getString(COL_CHANGE_AT)
                );
                logs.add(log);
            }
        }
    }
    return logs;
}

    //đếm số lượng bản ghi
    public int getTotalFilteredAuditLogs(String tableName, String actionType, String changedBy,
                                     String fromDate, String toDate) throws SQLException {
    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM AuditLog WHERE 1=1 ");
    List<Object> params = new ArrayList<>();

    if (tableName != null && !tableName.isEmpty()) {
        sql.append(" AND table_name = ?");
        params.add(tableName);
    }
    if (actionType != null && !actionType.isEmpty()) {
        sql.append(" AND action_type = ?");
        params.add(actionType);
    }
    if (changedBy != null && !changedBy.isEmpty()) {
        sql.append(" AND changed_by = ?");
        params.add(Integer.parseInt(changedBy));
    }
    if (fromDate != null && !fromDate.isEmpty()) {
        sql.append(" AND changed_at >= ?");
        params.add(fromDate + " 00:00:00");
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql.append(" AND changed_at <= ?");
        params.add(toDate + " 23:59:59");
    }

    try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }
    return 0;
}

    
    public void insertAuditLog(String tableName, int recordId, ActionType actionType, String message, int changedByUserId) throws SQLException {
    String sql = "INSERT INTO AuditLog (table_name, record_id, action_type, message, changed_by, changed_at) VALUES (?, ?, ?, ?, ?, NOW())";
    try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, tableName);
        ps.setInt(2, recordId);
        ps.setString(3, actionType.toString());
        ps.setString(4, message);
        ps.setInt(5, changedByUserId);
        ps.executeUpdate();
    }
}

    
}
