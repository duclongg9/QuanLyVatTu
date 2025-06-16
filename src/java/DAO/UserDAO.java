package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.User;

public class UserDAO extends DBConnect {

    // Kiểm tra thông tin đăng nhập
    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập!");
            e.printStackTrace();
        }

        return null;
    }

    // Thêm một người dùng mới
    public boolean insertUser(User user) {
        String sql = "INSERT INTO User (username, fullname, phone, password, email, address, gender, birthDate, roleId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullname());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getGender());
            stmt.setDate(8, user.getBirthDate());
            stmt.setInt(9, user.getRoleId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm user!");
            e.printStackTrace();
        }
        return false;
    }

    // Lấy user theo ID
    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy user theo ID!");
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách tất cả user
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(extractUserFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách user!");
            e.printStackTrace();
        }
        return list;
    }

    // Xóa user theo ID
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa user!");
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUser() throws Exception {
        List<User> list = new ArrayList<>();
        String sql = "SELECT username, password FROM User";

        Connection conn = getConnection(); // hoặc getConnection() phù hợp với project bạn
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User u = new User();
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password")); // mật khẩu đang là plain text
            list.add(u);
        }
        rs.close();
        ps.close();
        conn.close();
        return list;
    }

    public void updatePassword(String username, String hashedPassword) throws Exception {
        String sql = "UPDATE User SET password = ? WHERE username = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, hashedPassword);
        ps.setString(2, username);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    // Hàm hỗ trợ: đọc dữ liệu từ ResultSet và tạo đối tượng User
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFullname(rs.getString("fullname"));
        user.setPhone(rs.getString("phone"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setGender(rs.getString("gender"));
        user.setBirthDate(rs.getDate("birthDate"));
        user.setRoleId(rs.getInt("roleId"));
        return user;
    }
}
