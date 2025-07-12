package com.example.warehousemanagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/warehousemanagement?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Tải driver thủ công
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL được tải thành công!");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver MySQL: " + e.getMessage());
            throw new ServletException("Không tìm thấy driver MySQL", e);
        }

        // Debug: Test kết nối
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Kết nối MySQL thành công từ AddUserServlet!");
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối MySQL: " + e.getMessage());
            throw new ServletException("Lỗi kết nối database", e);
        }

        // Lấy tham số từ form
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String birth = request.getParameter("birth");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String roleId = request.getParameter("roleId");
        String status = request.getParameter("status");

        // Debug: In tham số
        System.out.println("Name: " + name);
        System.out.println("Sex: " + sex);
        System.out.println("Birth: " + birth);
        System.out.println("Address: " + address);
        System.out.println("Email: " + email);
        System.out.println("Account: " + account);
        System.out.println("Password: " + password);
        System.out.println("RoleId: " + roleId);
        System.out.println("Status: " + status);

        // Server-side validation
        String error = null;
        if (name == null || name.trim().isEmpty()) {
            error = "Tên không được để trống!";
        } else if (email == null || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            error = "Email không hợp lệ!";
        } else if (account == null || account.trim().isEmpty()) {
            error = "Tài khoản không được để trống!";
        } else if (password == null || password.trim().isEmpty()) {
            error = "Mật khẩu không được để trống!";
        } else if (roleId == null || roleId.trim().isEmpty()) {
            error = "Vui lòng chọn vai trò!";
        } else if (status == null || status.trim().isEmpty()) {
            error = "Vui lòng chọn trạng thái!";
        } else if (birth == null || birth.trim().isEmpty()) {
            error = "Ngày sinh không được để trống!";
        } else {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Role WHERE id = ?")) {
                stmt.setInt(1, Integer.parseInt(roleId));
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    error = "Vai trò không tồn tại!";
                }
            } catch (SQLException | NumberFormatException e) {
                error = "Lỗi vai trò: " + e.getMessage();
                System.err.println("Lỗi kiểm tra Role: " + e.getMessage());
            }
        }

        if (error != null) {
            System.err.println("Lỗi validation: " + error);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/add_user.jsp").forward(request, response);
            return;
        }

        // Hash password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Hashed Password: " + hashedPassword);

        // Insert vào database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Staff (Name, Sex, Birth, Address, Email, Account, Password, Role_Id, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setBoolean(2, sex.equals("1"));
            stmt.setDate(3, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(birth).getTime()));
            stmt.setString(4, address != null ? address : "");
            stmt.setString(5, email);
            stmt.setString(6, account);
            stmt.setString(7, hashedPassword);
            stmt.setInt(8, Integer.parseInt(roleId));
            stmt.setBoolean(9, status.equals("1"));

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Số dòng đã thêm: " + rowsAffected);
            if (rowsAffected > 0) {
                response.sendRedirect("user_success.jsp");
            } else {
                request.setAttribute("error", "Không thể thêm người dùng!");
                request.getRequestDispatcher("/add_user.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/add_user.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Lỗi khác: " + e.getMessage());
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/add_user.jsp").forward(request, response);
        }
    }
}