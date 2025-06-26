package controller.profile;

import dao.user.UserDAO;
import model.User;
import units.Validator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@MultipartConfig
@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/updateprofile"})
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("account");

        if (currentUser == null) {
            System.out.println("No logged-in user in session, redirecting to error.jsp");
            response.sendRedirect("error.jsp");
            return;
        }

        // Sử dụng user từ session thay vì chỉ dựa vào id từ URL
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("jsp/profile/updateprofile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }
         // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("account");
        if (currentUser == null || currentUser.getId() != id) {
            response.sendRedirect("error.jsp");
            return;
        }

        // Lấy và chuẩn hóa input
        
        
        String email = request.getParameter("email") != null ? request.getParameter("email").trim() : "";
        String phone = request.getParameter("phone") != null ? request.getParameter("phone").trim() : "";
        String address = request.getParameter("address") != null ? request.getParameter("address").trim().replaceAll("\\s+", " ") : "";
        String birthDate = request.getParameter("birthDate") != null ? request.getParameter("birthDate").trim() : "";
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));

        User user = new User(id,email, phone, address, gender, birthDate);

       

        // Validate input
        if (Validator.isNullOrEmpty(email) || Validator.isNullOrEmpty(phone)
                || Validator.isNullOrEmpty(address) 
                || Validator.isNullOrEmpty(birthDate)) {
            request.setAttribute("msg", "Vui lòng điền đầy đủ thông tin.");
            request.setAttribute("user", user);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("updateprofile.jsp").forward(request, response);
            return;
        }

        if (!Validator.isValidEmail(email)) {
            request.setAttribute("msg", "Email không hợp lệ.");
            request.setAttribute("user", user);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("updateprofile.jsp").forward(request, response);
            return;
        }

        if (!Validator.isValidPhone(phone)) {
            request.setAttribute("msg", "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.");
            request.setAttribute("user", user);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("updateprofile.jsp").forward(request, response);
            return;
        }

        if (!Validator.isValidAddress(address)) {
            request.setAttribute("msg", "Địa chỉ không hợp lệ! Không được chứa ký tự đặc biệt.");
            request.setAttribute("user", user);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("updateprofile.jsp").forward(request, response);
            return;
        }

        // Cập nhật nếu hợp lệ
        UserDAO dao = new UserDAO();
        boolean success = dao.updateUserProfile(id, email, phone, address, gender, birthDate);

        if (success) {
            User updatedUser = dao.getUserById(id);
            session.setAttribute("account", updatedUser);
            session.setAttribute("fullname", updatedUser.getFullName());
            session.setAttribute("email", updatedUser.getEmail());
            response.sendRedirect(request.getContextPath() + "/userprofile");
        } else {
            request.setAttribute("msg", "Cập nhật thất bại. Vui lòng thử lại.");
            request.setAttribute("user", user);
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("updateprofile.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý cập nhật thông tin người dùng và mật khẩu";
    }
}
