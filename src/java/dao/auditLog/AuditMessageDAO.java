/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.auditLog;

import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AuditMessage;

/**
 *
 * @author D E L L
 */
public class AuditMessageDAO {

    private static final String COL_ID = "id";
    private static final String COL_CODE = "code";
    private static final String COL_DECRIPTION = "description";

    public AuditMessage getAuditMessageById(int messageId) throws SQLException {
        String sql = "SELECT * FROM AuditMessageTemplate WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, messageId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    AuditMessage am = new AuditMessage();
                    am.setId(rs.getInt(COL_ID));
                    am.setCode(rs.getString(COL_CODE));
                    am.setText(rs.getString(COL_DECRIPTION));
                    return am;
                }
            }
        }
        return null;
    }
}
