/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RequestStatus;
/**
 *
 * @author D E L L
 */
public class requestStatusDAO {
    private final Connection conn;
    
    private static final String COL_ID = "id";
    private static final String COL_STATUS = "status";

    public requestStatusDAO() {
        conn = DBConnect.getConnection();
    }
    
     //Lấy trạng thái yêu cầu theo id
    public RequestStatus getStatusById(int id) {

        String sql ="""
                     SELECT * 
                     FROM requeststatus 
                     WHERE id =?
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    RequestStatus r = new RequestStatus();
                    r.setId(rs.getInt(COL_ID));
                    r.setStatus(rs.getString(COL_STATUS));
                    return r;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(RequestStatus.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
    
    
}
