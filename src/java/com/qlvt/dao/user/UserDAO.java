/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.dao.user;

import com.qlvt.dao.connect.DBConnect;
import com.qlvt.dao.role.RoleDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qlvt.model.Role;
import com.qlvt.model.User;

/**
 *
 * @author D E L L
 */
public class UserDAO {

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

//    private Connection conn;

    RoleDAO rdao = new RoleDAO();

    public UserDAO() {
//        conn = DBConnect.getConnection();
    }

    //Cập nhật mật khẩu mới vào database
     public boolean updatePassword(int userId, String hashedPassword) {
        String sql = "UPDATE User SET password=? WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     //Cập nhật thông tin người dùng vào database
    public boolean updateUserProfile(int id, String email, String phone, String address, boolean gender, String birthDate) {
        String sql = "UPDATE User SET email=?, phone=?, address=?, gender=?, birthDate=? WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setBoolean(4, gender);
            ps.setString(5, birthDate);
            ps.setInt(6, id);
            int rows = ps.executeUpdate();
            return rows > 0; // true nếu có ít nhất 1 dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
     // Kiểm tra thông tin đăng nhập
    public User checkLogin(String username, String password) {
        String sql = """
        SELECT *
        FROM User  
        WHERE username = ? AND password = ? 
        LIMIT 1;
    """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Lấy thông tin User
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("fullname"));
                user.setGender(rs.getBoolean("gender"));
                user.setBirthDate(rs.getString("birthDate"));
                user.setAddress(rs.getString("address"));
                user.setImage(rs.getString("image"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setStatus(rs.getBoolean("status"));
                user.setRole(rdao.getRoleById(rs.getInt("roleId"))); // Gán role đầy đủ vào user
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập!");
            e.printStackTrace();
        }

        return null;
    }
   
    
    //Kiểm tra email đã tồn tại chưa
    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM User WHERE email = ?";
         try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // tồn tại nếu có kết quả
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //kiểm tra xem đã có username này hay chưa
    public boolean isUserName(String username){
        String sql="SELECT 1 FROM User WHERE username = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //kiểm tra xem đã có CEO hay chưa
    public boolean isCEOExit() {
        String sql = "SELECT 1 FROM User WHERE roleId = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, 4);
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
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // tồn tại nếu có kết quả
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Kiểm tra số điện thoại hợp lệ
    //Tạo mới User
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
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
            return affectedRows;
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

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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

    //Lấy user bằng id
    public User getUserById(int id) {

        String sql = "SELECT * FROM ql_vat_tu.user where id = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) { //Sử dụng try-with-Resource để đóng tài nguyên sau khi sử dụng


            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
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
                    return p;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public User getUserByRoleId(int roleId) {

        String sql = "SELECT * FROM ql_vat_tu.user where roleId = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) { //Sử dụng try-with-Resource để đóng tài nguyên sau khi sử dụng

            ps.setInt(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
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
                    return p;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    //Tìm kiếm nhân viên bằng tên
    public List<User> findStaffByName(String name, int index) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM user \n"
                + "WHERE fullName LIKE ? LIMIT ? OFFSET ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) { //Sử dụng try-with-Resource để đóng tài nguyên sau khi sử dụng


            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);            
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
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
            }

        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    public int getTotalStaffBySearchName(String name) {
        String sql = "SELECT COUNT(*) FROM user Where fullName Like ? ;";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
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

    //Xóa mềm nhân viên bằng ID
    public void deleteStaffById(int id) {
        String getStatus = "SELECT status FROM user WHERE id = ?";
        String updateSql = "UPDATE user SET status = ? WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement getStatusPs = conn.prepareStatement(getStatus)) {

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

    //Phân trang
    public List<User> pagingStaff(int index) throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user\n"
                + "LIMIT ? OFFSET ?;";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

    //đếm số lượng người dùng trong database
    public int getTotalStaff() {
        String sql = "SELECT COUNT(*) FROM user;";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
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

    //Lấy danh sách người dùng trong database
    public List<User> getListUser() {
        List<User> list = new ArrayList<>();
        String sql = """
                     SELECT * FROM user
                     ORDER BY id DESC
                     """;
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Chỉ lấy sản phẩm theo cId
            try (ResultSet rs = ps.executeQuery()) {
                list.clear(); // Xóa danh sách cũ nếu có
                while (rs.next()) { // Lặp qua từng bản ghi trong bảng User
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
                return list;
            }

        } catch (SQLException ex) {

            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<User> getUsersByRoleId(int roleId, int index) throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM User WHERE roleId = ? LIMIT ?, ?;";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            ps.setInt(3, PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                list.clear(); // Xóa danh sách cũ nếu có
                while (rs.next()) { // Lặp qua từng bản ghi trong bảng User
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
                return list;
            } catch (SQLException ex) {

                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }

    }

    public int getTotalUserByRoleId(int roleId) {
        String sql = "SELECT COUNT(*) FROM user Where roleId = ?;";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, roleId);
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
    
   

    public static void main(String[] args) throws SQLException {
        UserDAO udao = new UserDAO();
        String name = "n";
//        udao.deleteStaffById(1);
        List<User> list = udao.findStaffByName(name, 1);

        int count = udao.getTotalStaffBySearchName(name);
//        System.out.println(count);
        for (User staff : list) {
            System.out.println(staff);
        }

//        System.out.println(udao.updateUser(7, false, 2));
    }

}
