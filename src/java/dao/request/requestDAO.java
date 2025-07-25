/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.auditLog.AuditLogDAO;
import dao.connect.DBConnect;
import dao.material.MaterialItemDAO;
import dao.user.UserDAO;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;
import model.ActionType;
import model.Request;
import model.RequestDetail;
import model.RequestType;
import model.User;
import model.importDecriptionDTO;

/**
 *
 * @author D E L L
 */
public class requestDAO {

    private Connection conn;
    UserDAO udao = new UserDAO();
    requestStatusDAO rsdao = new requestStatusDAO();

    private static final int PAGE_SIZE = 5;

    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_STATUS = "statusId";
    private static final String COL_USER = "userId";
    private static final String COL_NOTE = "note";
    private static final String COL_TYPE = "type";
    private static final String COL_APPROVEDBY = "approvedBy";

    public requestDAO(Connection conn) {
        this.conn = conn;
    }

    public requestDAO() {
        this(DBConnect.getConnection());
    }

    
    public importDecriptionDTO getRequestByInputWarehouseId(int inputWarehouseId) {
        importDecriptionDTO decription = null;
        String sql = "SELECT r.id, r.type, r.note " +
                     "FROM InputWarehouse iw " +
                     "JOIN InputDetail id ON iw.id = id.inputWarehouseId " +
                     "JOIN requestDetail rd ON id.requestDetailId = rd.id " +
                     "JOIN Request r ON rd.requestId = r.id " +
                     "WHERE iw.id = ? " +
                     "LIMIT 1";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inputWarehouseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                decription = new importDecriptionDTO();
                decription.setType(rs.getString("type"));
                decription.setNote(rs.getString("note"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decription;
    }


    
    
    public boolean updateSuccessStatusRequest(int requestId, int changedBy) {
        String sql = "UPDATE Request SET statusId = 5 WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);

            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "Yêu cầu phụ trách bởi User: " + user.getFullName() + "với ID: " + getRequestById(requestId).getId() + " đã hoàn thành";
            logDAO.insertAuditLog("Category", requestId, ActionType.UPDATE, message, changedBy);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusRequest(int requestId, int statusId, int userId, int changedBy) {
        String sql = (userId == 0)
                ? "UPDATE Request SET statusId = ?, approvedBy = NULL WHERE id = ?"
                : "UPDATE Request SET statusId = ?, approvedBy = ? WHERE id = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, statusId);
            if (userId == 0) {
                ps.setInt(2, requestId);
            } else {
                ps.setInt(2, userId);
                ps.setInt(3, requestId);
            }

            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã cập nhật lại trạng thái yêu cầu với ID: " + requestId;
            logDAO.insertAuditLog("Request", requestId, ActionType.UPDATE, message, changedBy);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getPriceForMaterialItem(int materialId, int supplierId) throws SQLException {
        String sql = "SELECT price FROM MaterialItem WHERE materialId = ? AND supplierId = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, materialId);
            ps.setInt(2, supplierId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("price");
                }
            }
        }
        return 0.0; // mặc định nếu không tìm thấy
    }

    public void updateStockQuantity(int materialId, int supplierId, int addedQuantity) throws SQLException {
        String sql = "UPDATE MaterialItem SET stockQuantity = stockQuantity + ? WHERE materialId = ? AND supplierId = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addedQuantity);
            ps.setInt(2, materialId);
            ps.setInt(3, supplierId);
            ps.executeUpdate();
        }
    }

    public List<RequestDetail> getRequestDetailByRequestId(int requestId) throws SQLException {
        List<RequestDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM RequestDetail WHERE requestId = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RequestDetail detail = new RequestDetail();
                    detail.setId(rs.getInt("id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setNote(rs.getString("note"));

                    MaterialItemDAO midao = new MaterialItemDAO();

                    detail.setMaterialItem(midao.getMaterialItemById(rs.getInt("materialItemId")));

                    // Gán requestId nếu model có:
                    // requestDAO rdao = new requestDAO();
                    // detail.setRequestId(rdao.getRequestById(requestId)); // nếu cần object Request
                    list.add(detail);
                }
            }
        }
        return list;
    }

    public void updateRequestDetail(int detailId, int quantity, String note) throws SQLException {
        String sql = "UPDATE RequestDetail SET quantity = ?, note = ? WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);  // cập nhật quantity
            ps.setString(2, note);   // cập nhật note
            ps.setInt(3, detailId);  // biết dòng nào cần update

            ps.executeUpdate(); // chạy lệnh update
        }
    }

    // Thêm request mới, trả về requestId vừa tạo
    public int insertRequest(int userId, String note, Integer approverId, RequestType type, int changedBy) throws SQLException {
        String sql = "INSERT INTO Request (date, statusId, userId, note, type, approvedBy) VALUES (NOW(), ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, 1); // statusId: 1 = Pending
            ps.setInt(2, userId);
            ps.setString(3, note);
            ps.setString(4, type.name());

            if (approverId != null) {
                ps.setInt(5, approverId);
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            int requestId = -1;
            ps.executeUpdate(); // Thực hiện insert

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    requestId = rs.getInt(1);
                }
            }

            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " Đã tạo mới Yêu cầu:";
            logDAO.insertAuditLog("Supplier", requestId, ActionType.INSERT, message, changedBy);

            if (requestId == 0) {
                throw new SQLException("Creating request failed, no rows affected.");
            }

            // Lấy ID của request mới tạo
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về requestId vừa tạo
                } else {
                    throw new SQLException("Creating request failed, no ID obtained.");
                }
            }
        }
    }

    // Thêm chi tiết các vật tư cho Request vào bảng RequestDetail
    public void insertRequestDetail(int requestId, int materialItemId, int quantity, String note) throws SQLException {
        String sql = "INSERT INTO RequestDetail (requestId, materialItemId, quantity, note) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ps.setInt(2, materialItemId); // chỉ cần id dòng MaterialItem
            ps.setInt(3, quantity);
            ps.setString(4, note);
            ps.executeUpdate();
        }
    }

    public List<Request> getAllRequest() {
        List<Request> list = new ArrayList<>();
        String sql = " SELECT * FROM request ORDER BY id DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                list.clear();
                while (rs.next()) {
                    Request r = new Request();
                    r.setId(rs.getInt(COL_ID));
                    r.setDate(rs.getString(COL_DATE));
                    r.setStatusId(rsdao.getStatusById(rs.getInt(COL_STATUS)));
                    r.setUserId(udao.getUserById(rs.getInt(COL_USER)));
                    r.setNote(rs.getString(COL_NOTE));
                    String typeStr = rs.getString(COL_TYPE); // Lấy giá trị từ DB: "Import" hoặc "Export"
                    RequestType type = RequestType.valueOf(typeStr.toUpperCase()); // Convert String -> Enum
                    r.setType(type); // set đúng kiểu RequestType
                    r.setApprovedBy(udao.getUserById(rs.getInt(COL_APPROVEDBY)));
                    list.add(r);
                }
                return list;
            }

        } catch (SQLException ex) {

            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //đếm số lượng yêu cầu trong database
    public int getFilteredTotalRequest(Integer statusId, String type, String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM request WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (statusId != null && statusId != 0) {
            sql.append(" AND statusId = ?");
            params.add(statusId);
        }
        if (type != null && !"0".equals(type)) {
            sql.append(" AND type = ?");
            params.add(type);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND date >= ?");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND date <= ?");
            params.add(toDate);
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    //Phân trang
    public List<Request> pagingRequestWithFilter(int index, Integer statusId, String type, String fromDate, String toDate) throws SQLException {
        List<Request> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM request WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (statusId != null && statusId != 0) {
            sql.append(" AND statusId = ?");
            params.add(statusId);
        }
        if (type != null && !"0".equals(type)) {
            sql.append(" AND type = ?");
            params.add(type);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND date >= ?");
            params.add(fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND date <= ?");
            params.add(toDate);
        }

        sql.append(" ORDER BY id DESC LIMIT ? OFFSET ?");
        params.add(PAGE_SIZE);
        params.add((index - 1) * PAGE_SIZE);

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setId(rs.getInt(COL_ID));
                    r.setDate(rs.getString(COL_DATE));
                    r.setStatusId(rsdao.getStatusById(rs.getInt(COL_STATUS)));
                    r.setUserId(udao.getUserById(rs.getInt(COL_USER)));
                    r.setNote(rs.getString(COL_NOTE));

                    String typeStr = rs.getString(COL_TYPE);
                    RequestType requestType = RequestType.valueOf(typeStr.toUpperCase());
                    r.setType(requestType);

                    r.setApprovedBy(udao.getUserById(rs.getInt(COL_APPROVEDBY)));
                    list.add(r);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public Request getRequestById(int id) {

        String sql = "SELECT * FROM ql_vat_tu.request where id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) { //Sử dụng try-with-Resource để đóng tài nguyên sau khi sử dụng

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setId(rs.getInt(COL_ID));
                    r.setDate(rs.getString(COL_DATE));
                    r.setStatusId(rsdao.getStatusById(rs.getInt(COL_STATUS)));
                    r.setUserId(udao.getUserById(rs.getInt(COL_USER)));
                    r.setNote(rs.getString(COL_NOTE));
                    String typeStr = rs.getString(COL_TYPE); // Lấy giá trị từ DB: "Import" hoặc "Export"
                    RequestType type = RequestType.valueOf(typeStr.toUpperCase()); // Convert String -> Enum
                    r.setType(type); // set đúng kiểu RequestType
                    r.setApprovedBy(udao.getUserById(rs.getInt(COL_APPROVEDBY)));
                    return r;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    //Phân trang
    public List<Request> pagingRequestPending(int index, String fromDate, String toDate) throws SQLException {
        List<Request> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM request WHERE statusId = 1");

        // Thêm điều kiện lọc ngày nếu có
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND requestDate >= ?");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND requestDate <= ?");
        }

        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(paramIndex++, fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(paramIndex++, toDate);
            }

            ps.setInt(paramIndex++, PAGE_SIZE);
            ps.setInt(paramIndex, (index - 1) * PAGE_SIZE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setId(rs.getInt(COL_ID));
                    r.setDate(rs.getString(COL_DATE));
                    r.setStatusId(rsdao.getStatusById(rs.getInt(COL_STATUS)));
                    r.setUserId(udao.getUserById(rs.getInt(COL_USER)));
                    r.setNote(rs.getString(COL_NOTE));

                    String typeStr = rs.getString(COL_TYPE);
                    RequestType requestType = RequestType.valueOf(typeStr.toUpperCase());
                    r.setType(requestType);

                    r.setApprovedBy(udao.getUserById(rs.getInt(COL_APPROVEDBY)));
                    list.add(r);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    //đếm số request với trạng thái pending
    public int getTotalRequestPending(String fromDate, String toDate) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM request WHERE statusId = 1");

    if (fromDate != null && !fromDate.isEmpty()) {
        sql.append(" AND requestDate >= ?");
    }
    if (toDate != null && !toDate.isEmpty()) {
        sql.append(" AND requestDate <= ?");
    }

    try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int paramIndex = 1;
        if (fromDate != null && !fromDate.isEmpty()) {
            ps.setString(paramIndex++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            ps.setString(paramIndex++, toDate);
        }

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

    } catch (Exception e) {
        Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
    }

    return 0;
}


    public static void main(String[] args) throws SQLException {
        requestDAO rdao = new requestDAO();

//        udao.deleteStaffById(1);
//        System.out.println(udao.updateUser(7, false, 2));
    }

}
