/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.connect.DBConnect;
import dao.user.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.InputWarehourse;
import model.User;

/**
 *
 * @author D E L L
 */
public class InputWarehourseDAO {
    
    UserDAO udao = new UserDAO();
    
    public List<InputWarehourse> getAllInputWarehouses() throws SQLException {
    List<InputWarehourse> list = new ArrayList<>();
    String sql = "SELECT * FROM InputWarehouse";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            InputWarehourse warehouse = new InputWarehourse();
            warehouse.setId(rs.getInt("id"));
            warehouse.setDateInput(rs.getString("dateInput"));
            // Lấy User nếu cần
            int userId = rs.getInt("userId");
            User user = new User(); // hoặc dùng UserDAO.getUserById(userId) nếu cần
            user.setId(userId);
            warehouse.setUserId(udao.getUserById(userId));

            list.add(warehouse);
        }
    }
    return list;
}

    
    
     // Insert phiếu nhập kho mới, trả về inputWarehouseId vừa tạo
    public int insertInputWarehouse(int userId) throws SQLException {
        String sql = "INSERT INTO InputWarehouse (dateInput, userId) VALUES (NOW(), ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
}
