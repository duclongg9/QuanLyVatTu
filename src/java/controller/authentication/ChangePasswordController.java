package controller.authentication;

import dao.user.UserDAO;
import model.User;
import units.Encoding;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
@MultipartConfig
@WebServlet(name = "changePassword", urlPatterns = {"/changepassword"})
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.setAttribute("user", loggedInUser);
        request.getRequestDispatcher("/jsp/profile/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Mã hóa mật khẩu hiện tại
        String hashedCurrent = Encoding.toSHA1(currentPassword);

        if (!hashedCurrent.equals(loggedInUser.getPassword())) {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
            request.getRequestDispatcher("jsp/profile/changePassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Xác nhận mật khẩu mới không khớp.");
            request.getRequestDispatcher("jsp/profile/changePassword.jsp").forward(request, response);
            return;
        }
        
         
    if (newPassword.length() < 6 || newPassword.length() > 20) {
        request.setAttribute("error", "Mật khẩu phải có độ dài từ 6 đến 20 ký tự.");
        request.getRequestDispatcher("jsp/profile/changePassword.jsp").forward(request, response);
        return;
    }

        String hashedNew = Encoding.toSHA1(newPassword);
        UserDAO dao = new UserDAO();
        boolean updated = dao.updatePassword(loggedInUser.getId(), hashedNew);

        if (updated) {
            session.setAttribute("msg", "Đổi mật khẩu thành công. Vui lòng đăng nhập lại bằng mật khẩu mới.");
            session.removeAttribute("account");
            response.sendRedirect("login.jsp");
            return;
        } else {
            request.setAttribute("error", "Đổi mật khẩu thất bại.");
        }

        request.getRequestDispatcher("jsp/profile/changePassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Đổi mật khẩu người dùng";
    }
}
