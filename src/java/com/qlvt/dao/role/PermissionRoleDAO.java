package com.qlvt.dao.role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.qlvt.model.Permission;
import com.qlvt.model.PermissionRole;
import com.qlvt.model.Role;
import com.qlvt.dao.connect.DBConnect;

public class PermissionRoleDAO extends DBConnect {

    // Lấy tất cả các quyền theo vai trò
    public List<PermissionRole> getByRoleId(int roleId) {
        List<PermissionRole> list = new ArrayList<>();
        String sql = "SELECT pr.id, pr.status, "
                + "r.id as roleId, r.role as roleName, "
                + "p.id as permissionId, p.url, p.description "
                + "FROM permission_role pr "
                + "JOIN role r ON pr.roleId = r.id "
                + "JOIN permission p ON pr.permissionId = p.id "
                + "WHERE pr.roleId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role(rs.getInt("roleId"),
                        rs.getString("roleName"));
                Permission permission = new Permission(rs.getInt("permissionId"),
                        rs.getString("url"),
                        rs.getString("description"));
                PermissionRole pr = new PermissionRole(rs.getInt("id"), role, permission, rs.getBoolean("status"));
                list.add(pr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật trạng thái quyền theo roleId và permissionId
    public void updateStatus(int roleId, int permissionId, boolean status) {
        String sql = "UPDATE permission_role SET status = ? WHERE roleId = ? AND permissionId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setInt(2, roleId);
            ps.setInt(3, permissionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   public boolean updatePermissionStatus(int id, boolean status) {
    String sql = "UPDATE permission_role SET status = ? WHERE id = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setBoolean(1, status);
        ps.setInt(2, id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // Trả về true nếu có ít nhất 1 dòng được cập nhật
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Có lỗi xảy ra thì trả về false
    }
}

    // Thêm quyền mới cho vai trò
    public void insert(PermissionRole pr) {
        String sql = "INSERT INTO permission_role (permissionId, roleId, status) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pr.getPermission().getId());
            ps.setInt(2, pr.getRole().getId());
            ps.setBoolean(3, pr.isStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa tất cả quyền của một vai trò (dùng khi reset toàn bộ)
    public void deleteByRoleId(int roleId) {
        String sql = "DELETE FROM permission_role WHERE roleId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean permissionCheck(int roleId, String url) {
        if (roleId == 1) return true;
        
        String sql = """
        SELECT 1 FROM permission_role pr
        JOIN permission p ON pr.permissionId = p.id
        WHERE pr.roleId = ? AND p.url = ? AND pr.status = 1
        LIMIT 1
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setString(2, url);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // nếu có dòng trả về -> có quyền
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<PermissionRole> getAllPermissionRoles() {
    List<PermissionRole> list = new ArrayList<>();
    
    String sql = """
        SELECT pr.id AS pr_id, pr.status,
               r.id AS role_id, r.role AS role_name,
               p.id AS permission_id, p.url, p.description
        FROM permission_role pr
        JOIN role r ON pr.roleId = r.id
        JOIN permission p ON pr.permissionId = p.id
        
    """;

    try (Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            // Tạo đối tượng Role
            Role role = new Role(
                rs.getInt("role_id"),
                rs.getString("role_name")
            );

            // Tạo đối tượng Permission
            Permission permission = new Permission(
                rs.getInt("permission_id"),
                rs.getString("url"),
                rs.getString("description")
            );

            // Tạo đối tượng PermissionRole
            PermissionRole pr = new PermissionRole(
                rs.getInt("pr_id"),
                role,
                permission,
                rs.getBoolean("status")
            );

            list.add(pr);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

    public void upsertPermissionRole(int permissionId, int roleId, boolean status) {
    try {
        String checkSql = "SELECT id FROM permission_role WHERE permissionId = ? AND roleId = ?";
        Connection connection = getConnection();
        PreparedStatement checkStmt = connection.prepareStatement(checkSql);
        checkStmt.setInt(1, permissionId);
        checkStmt.setInt(2, roleId);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            // Nếu tồn tại => update
            int id = rs.getInt("id");
            updatePermissionStatus(id, status);
        } else {
            // Nếu chưa tồn tại => insert
            String insertSql = "INSERT INTO permission_role (permissionId, roleId, status) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setInt(1, permissionId);
            insertStmt.setInt(2, roleId);
            insertStmt.setBoolean(3, status);
            insertStmt.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}
