/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.connect.DBConnect;
import java.sql.*;

/**
 *
 * @author D E L L
 */
public class InputDetailDAO {
    
    
     public void insertInputDetail(int inputWarehouseId, int requestDetailId, int materialId, int supplierId, int quantity, double inputPrice) throws SQLException {
        String sql = "INSERT INTO InputDetail (quantity, inputWarehouseId, requestDetailId, materialId, supplierId, inputPrice) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, inputWarehouseId);
            ps.setInt(3, requestDetailId);
            ps.setInt(4, materialId);
            ps.setInt(5, supplierId);
            ps.setDouble(6, inputPrice); // giả sử lấy từ form hoặc mặc định 0
            ps.executeUpdate();
        }
    }
}
