package dao;
import model.Role;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Userdao {
    private final Connection conn;

    public Userdao() {
        conn = DBConnect.getConnection();
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM user WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPhoneExits(String phone) {
        String sql = "SELECT 1 FROM user WHERE phone = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(String username, String fullname, String phone, String password, String email,
                              String address, boolean gender, String birthDate, String image, boolean status, int roleId) {
        String sql = "INSERT INTO user (username, fullname, phone, password, email, address, gender, birthDate, image, status, role_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, fullname);
            ps.setString(3, phone);
            ps.setString(4, password);
            ps.setString(5, email);
            ps.setString(6, address);
            ps.setBoolean(7, gender);
            ps.setString(8, birthDate);
            ps.setString(9, image);
            ps.setBoolean(10, status);
            ps.setInt(11, roleId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT u.*, r.id AS rid, r.role AS roleName FROM user u JOIN role r ON u.role_id = r.id";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt("rid"), rs.getString("roleName"));
                User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("fullname"),
                        rs.getString("phone"), rs.getString("password"), rs.getString("email"),
                        rs.getString("address"), rs.getBoolean("gender"), rs.getString("birthDate"),
                        rs.getString("image"), rs.getBoolean("status"), r);
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserById(int id) {
        String sql = "SELECT u.*, r.id AS rid, r.role AS roleName FROM user u JOIN role r ON u.role_id = r.id WHERE u.id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Role r = new Role(rs.getInt("rid"), rs.getString("roleName"));
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("fullname"),
                        rs.getString("phone"), rs.getString("password"), rs.getString("email"),
                        rs.getString("address"), rs.getBoolean("gender"), rs.getString("birthDate"),
                        rs.getString("image"), rs.getBoolean("status"), r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserStatusAndRole(int uid, int roleId, boolean status) {
        String sql = "UPDATE user SET role_id = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setBoolean(2, status);
            ps.setInt(3, uid);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
