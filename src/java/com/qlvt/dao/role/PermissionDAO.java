package com.qlvt.dao.role;

import com.qlvt.dao.connect.DBConnect;
import com.qlvt.model.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO extends DBConnect {

    // Lấy toàn bộ danh sách permission
    public List<Permission> getAllPermissions() {
        List<Permission> list = new ArrayList<>();
        String sql = "SELECT * FROM permission";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Permission permission = new Permission(
                    rs.getInt("id"),
                    rs.getString("url"),
                    rs.getString("description")
                );
                list.add(permission);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy permission theo ID
    public Permission getById(int id) {
        String sql = "SELECT * FROM permission WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Permission(
                    rs.getInt("id"),
                    rs.getString("url"),
                    rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm mới permission
    public void insert(Permission p) {
        String sql = "INSERT INTO permission (url, description) VALUES (?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUrl());
            ps.setString(2, p.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật permission
    public void update(Permission p) {
        String sql = "UPDATE permission SET url = ?, description = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getUrl());
            ps.setString(2, p.getDescription());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa permission theo ID
    public void delete(int id) {
        String sql = "DELETE FROM permission WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
