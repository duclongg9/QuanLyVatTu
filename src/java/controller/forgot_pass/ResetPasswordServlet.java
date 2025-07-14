/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.forgot_pass;

import dao.user.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import units.Encoding;

/**
 *
 * @author admin
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/resetpassword"})
public class ResetPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResetPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("jsp/forgotpassword/resetpassword.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newPassword = request.getParameter("newpassword");
        String confirmPassword = request.getParameter("confirmpassword");
        HttpSession session = request.getSession(false);
        String email = (session != null) ? (String) session.getAttribute("email") : null;

// Kiểm tra phiên làm việc và email
        if (email == null) {
            response.sendRedirect("forgotpassword?error=Hết phiên làm việc. Vui lòng thử lại.");
            return;
        }

// Kiểm tra xác nhận mật khẩu
        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("jsp/forgotpassword/resetpassword.jsp").forward(request, response);
            return;
        }

// Kiểm tra định dạng mật khẩu
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[\\S]{6,20}$";
        if (!newPassword.matches(passwordPattern)) {
            request.setAttribute("error", "Mật khẩu phải từ 6-20 ký tự và chứa ít nhất một chữ cái và một số.");
            request.getRequestDispatcher("jsp/forgotpassword/resetpassword.jsp").forward(request, response);
            return;
        }

// Mã hóa mật khẩu
        String hashedPassword = Encoding.toSHA1(newPassword);
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.updatePasswordByEmail(email, hashedPassword);

        if (success) {
            session.invalidate();
            request.setAttribute("msg", "Đổi mật khẩu thành công.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Cập nhật mật khẩu thất bại!");
            request.getRequestDispatcher("jsp/forgotpassword/resetpassword.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
