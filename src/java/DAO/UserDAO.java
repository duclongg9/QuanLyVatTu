package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.User;


public class UserDAO extends DBConnect {
    
    private static final int PAGE_SIZE = 5;

    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_FULLNAME = "fullname";
    private static final String COL_PHONE = "phone";
    private static final String COL_PASSWORD = "password";
    private static final String COL_EMAIL = "email";
    private static final String COL_ADDRESS = "address";
    private static final String COL_GENDER = "gender";
    private static final String COL_BIRTHDATE = "birthDate";
    private static final String COL_IMAGE = "image";
    private static final String COL_STATUS = "status";
    private static final String COL_ROLEID = "roleId";
    
    RoleDAO rdao = new RoleDAO();

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
 public int createUser(
            String username,
            String fullname,
            String phone,
            String password,
            String email,
            String address,
            boolean gender,
            String birthDate,
            String image,
            boolean status,
            int roleId) {
        String sql = "CALL CreateNewUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
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
            int affectedRows = ps.executeUpdate();
            return affectedRows ;
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
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
        Role role = new Role();
        role.setId(rs.getInt("roleId"));

        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("fullname"));
        user.setPhone(rs.getString("phone"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setGender(rs.getBoolean("gender"));
        user.setBirthDate(rs.getString("birthDate"));
        user.setImage(rs.getString("image"));
        user.setStatus(rs.getBoolean("status"));
        user.setRole(role);
        return user;
    }
  
  //Phân trang
    public List<User> pagingStaff(int index) throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user\n"
                + "LIMIT ? OFFSET ?;";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    User p = new User();
                    p.setId(rs.getInt(COL_ID));
                    p.setUsername(rs.getString(COL_USERNAME));
                    p.setFullName(rs.getString(COL_FULLNAME));
                    p.setPhone(rs.getString(COL_PHONE));
                    p.setPassword(rs.getString(COL_PASSWORD));
                    p.setEmail(rs.getString(COL_EMAIL));
                    p.setAddress(rs.getString(COL_ADDRESS));
                    p.setGender(rs.getBoolean(COL_GENDER));
                    p.setBirthDate(rs.getString(COL_BIRTHDATE));
                    p.setImage(rs.getString(COL_IMAGE));
                    p.setStatus(rs.getBoolean(COL_STATUS));
                    p.setRole(rdao.getRoleById(rs.getInt(COL_ROLEID)));
                    list.add(p);
                }
            } catch (Exception e) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
            }

            return list;
        }
    }
    
    //Xóa mềm nhân viên bằng ID
    public void deleteStaffById(int id) {
        String getStatus = "SELECT status FROM user WHERE id = ?";
        String updateSql = "UPDATE user SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement getStatusPs = conn.prepareStatement(getStatus)) {

            getStatusPs.setInt(1, id);
            try (ResultSet rs = getStatusPs.executeQuery()) {
                if (rs.next()) {
                    boolean currentStatus = rs.getBoolean("status");
                    boolean newStatus = !currentStatus;
                    try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                        updatePs.setBoolean(1, newStatus);
                        updatePs.setInt(2, id);
                        updatePs.executeUpdate();
                    }

                }
            }

        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //đếm số lượng người dùng trong database
    public int getTotalStaff() {
        String sql = "SELECT COUNT(*) FROM user;";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }
//Cập nhật User
    public boolean updateUser(int id,
            boolean status,
            int roleId) {
        String sql = """
                    UPDATE User
                    SET status = ?, roleId = ?
                    WHERE id = ?
                    """;

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(3, id);
            ps.setBoolean(1, status);
            ps.setInt(2, roleId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
     //Kiểm tra email đã tồn tại chưa
    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM User WHERE email = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // tồn tại nếu có kết quả
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
     //kiểm tra xem số điện thoại này đã tồn tại chưa
    public boolean isPhoneExits(String phone) {
        String sql = "SELECT 1 FROM User WHERE phone = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // tồn tại nếu có kết quả
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
