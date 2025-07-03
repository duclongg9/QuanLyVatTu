/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.dao.request;

import com.qlvt.dao.connect.DBConnect;
import com.qlvt.dao.user.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qlvt.model.InputWarehourse;
import com.qlvt.model.Request;
import com.qlvt.model.RequestType;
import com.qlvt.model.User;

/**
 *
 * @author D E L L
 */
public class InputWarehourseDAO {
    private static final String COL_ID = "id";
    private static final String COL_DATEINPUT = "dateInput";
    private static final String COL_USERID = "userId";
    
    private Connection conn;
    
    UserDAO udao = new UserDAO();
    
    public InputWarehourse getInputWarehourseById(int inputWarehourseId){
        String sql = "SELECT * FROM inputwarehouse WHERE id = ?";
         try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) { //Sử dụng try-with-Resource để đóng tài nguyên sau khi sử dụng

            ps.setInt(1, inputWarehourseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InputWarehourse iw = new InputWarehourse();
                    iw.setId(rs.getInt(COL_ID));
                    iw.setDateInput(rs.getString(COL_DATEINPUT));
                    iw.setUserId(udao.getUserById(rs.getInt(COL_USERID)));
                    return iw;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
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

            int userId = rs.getInt("userId");
            User user = new User();
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
    public static void main(String[] args) {
        InputWarehourseDAO i = new InputWarehourseDAO();
        InputWarehourse imports = i.getInputWarehourseById(1);
        System.out.println(imports);
    }
}
