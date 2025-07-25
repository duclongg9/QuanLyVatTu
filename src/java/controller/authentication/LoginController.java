package controller.authentication;

import dao.user.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie; // Import lớp Cookie
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import units.Encoding;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        
        try {
            String username = request.getParameter("username");
            System.out.println(username);
            String password = request.getParameter("password");

            // Mã hóa mật khẩu bằng SHA-1 trước khi kiểm tra
            String hashedPassword = Encoding.toSHA1(password);

            HttpSession session = request.getSession();
            UserDAO aDAO = new UserDAO();
            User account = aDAO.checkLogin(username, hashedPassword);

            if (account == null) {
                request.setAttribute("msg", "Tài khoản hoặc mật khẩu không đúng!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else if (account.isStatus() == false) {
                request.setAttribute("msg", "Tài khoản của bạn bị khóa.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // Lưu thông tin vào session
                session.setAttribute("account", account);
                session.setAttribute("fullname", account.getFullName());
                session.setAttribute("email", account.getEmail());
                session.setAttribute("loggedInUser", account);
                session.setAttribute("roleName", account.getRole().getRoleName());
                session.setAttribute("role_id", account.getRole().getId());
                session.setAttribute("status", account.isStatus() ? 1 : 0);

                // Lưu cookie cho username
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(24 * 60 * 60); // Cookie sống trong 1 ngày (tính bằng giây)
                usernameCookie.setPath("/"); // Cookie có hiệu lực cho toàn bộ ứng dụng
                response.addCookie(usernameCookie); // Thêm cookie vào phản hồi

                // Lưu cookie cho email
                Cookie emailCookie = new Cookie("email", account.getEmail());
                emailCookie.setMaxAge(24 * 60 * 60); // Cookie sống trong 1 ngày
                emailCookie.setPath("/");
                response.addCookie(emailCookie);

                System.out.println("Đăng nhập thành công, cookie đã được lưu, chuyển hướng đến board.jsp");
                response.sendRedirect("home");

                //request.getRequestDispatcher("board.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi trong doPost: " + e.getMessage());
            request.setAttribute("msg", "Lỗi hệ thống, vui lòng thử lại!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng nhập người dùng";
    }
}