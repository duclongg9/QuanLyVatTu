/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.dao.export;

import com.qlvt.dao.connect.DBConnect;
import com.qlvt.dao.user.UserDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qlvt.model.OutputWarehourse;
import com.qlvt.model.RequestType;
import com.qlvt.model.User;

public class OutputWarehourseDAO {
    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_USERID = "userId";
    private static final String COL_TYPE = "type";
    private static final int PAGE_SIZE = 5;

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
                    String typeStr = rs.getString(COL_TYPE);
                    ow.setType(RequestType.valueOf(typeStr.toUpperCase()));
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
                String typeStr = rs.getString(COL_TYPE);
                ow.setType(RequestType.valueOf(typeStr.toUpperCase()));
                list.add(ow);
            }
        }
        return list;
    }

    // Count total export orders
    public int getTotalOutputWarehouse() {
        String sql = "SELECT COUNT(*) FROM OutputWarehouse";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(OutputWarehourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Pagination
    public List<OutputWarehourse> pagingOutputWarehouse(int index) throws SQLException {
        List<OutputWarehourse> list = new ArrayList<>();
        String sql = "SELECT * FROM OutputWarehouse LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OutputWarehourse ow = new OutputWarehourse();
                    ow.setId(rs.getInt(COL_ID));
                    ow.setDate(rs.getString(COL_DATE));
                    ow.setUserId(udao.getUserById(rs.getInt(COL_USERID)));
                    String typeStr = rs.getString(COL_TYPE);
                    ow.setType(RequestType.valueOf(typeStr.toUpperCase()));
                    list.add(ow);
                }
            }
        }
        return list;
    }

    // Search with pagination
    public List<OutputWarehourse> searchOutputWarehouse(String keyword, int index) throws SQLException {
        List<OutputWarehourse> list = new ArrayList<>();
        String sql = "SELECT ow.* FROM OutputWarehouse ow JOIN User u ON ow.userId = u.id " +
                     "WHERE ow.id LIKE ? OR u.fullname LIKE ? OR ow.date LIKE ? LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setInt(4, PAGE_SIZE);
            ps.setInt(5, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OutputWarehourse ow = new OutputWarehourse();
                    ow.setId(rs.getInt(COL_ID));
                    ow.setDate(rs.getString(COL_DATE));
                    ow.setUserId(udao.getUserById(rs.getInt(COL_USERID)));
                    String typeStr = rs.getString(COL_TYPE);
                    ow.setType(RequestType.valueOf(typeStr.toUpperCase()));
                    list.add(ow);
                }
            }
        }
        return list;
    }

    public int countSearchOutputWarehouse(String keyword) {
        String sql = "SELECT COUNT(*) FROM OutputWarehouse ow JOIN User u ON ow.userId = u.id " +
                     "WHERE ow.id LIKE ? OR u.fullname LIKE ? OR ow.date LIKE ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(OutputWarehourseDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }


    
    // Insert new output warehouse, return generated id
    public int insertOutputWarehouse(int userId, RequestType type) throws SQLException {
        String sql = "INSERT INTO OutputWarehouse (date, userId, type) VALUES (NOW(), ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setString(2, type.name());
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
