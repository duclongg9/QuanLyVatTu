/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.export;

import dao.connect.DBConnect;
import dao.user.UserDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OutputWarehourse;
import model.User;

public class OutputWarehourseDAO {
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_USERID = "userId";

    UserDAO udao = new UserDAO();

    public OutputWarehourse getOutputWarehourseById(int id) {
        String sql = "SELECT * FROM OutputWarehouse WHERE id = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OutputWarehourse ow = new OutputWarehourse();
                    ow.setId(rs.getInt(COL_ID));
                    ow.setDate(rs.getString(COL_DATE));
                    ow.setUserId(udao.getUserById(rs.getInt(COL_USERID)));
                    return ow;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(OutputWarehourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<OutputWarehourse> getAllOutputWarehouses() throws SQLException {
        List<OutputWarehourse> list = new ArrayList<>();
        String sql = "SELECT * FROM OutputWarehouse";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OutputWarehourse ow = new OutputWarehourse();
                ow.setId(rs.getInt(COL_ID));
                ow.setDate(rs.getString(COL_DATE));
                int userId = rs.getInt(COL_USERID);
                ow.setUserId(udao.getUserById(userId));
                list.add(ow);
            }
        }
        return list;
    }

    // Insert new output warehouse, return generated id
    public int insertOutputWarehouse(int userId) throws SQLException {
        String sql = "INSERT INTO OutputWarehouse (date, userId) VALUES (NOW(), ?)";
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
