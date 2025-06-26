/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dao.user.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import units.Encoding;
import units.RandomCode;
import units.SendMail;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "ResetPasswordController", urlPatterns = {"/resetPassword"})
public class ResetPasswordController extends HttpServlet {

    UserDAO udao = new UserDAO();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = udao.getUserById(userId);
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String userEmail = user.getEmail();
        String userName = user.getFullName();
        
        
        String newPassword = RandomCode.generateRandomString(8);
        String hashNewPassword = Encoding.toSHA1(newPassword);
        boolean updatedPassword = udao.updatePassword(userId, hashNewPassword);
        System.out.println(updatedPassword);
        if(updatedPassword){
            String subject = "[Thông báo quan trọng]-Thông tin tài khoản của bạn";
                String messageText = "Chào " + userName + ",\n\n"
                        + "Mật khẩu mới cho tài khoản của bạn là: " + newPassword + "\n\n"
                        + "Vui lòng đổi mật khẩu sau khi đăng nhập bằng mật khẩu mới.\n\n"
                        + "Trân trọng,\nAdmin Material Management";

                try {
                    SendMail.sendMail(userEmail, subject, messageText);
                } catch (Exception e) {
                    e.printStackTrace(); // log lỗi gửi mail (không làm hỏng luồng chính)
                }
                
                response.sendRedirect("userList");
        }else {
                request.setAttribute("error", "Reset Password User Failed");
                request.getRequestDispatcher("/jsp/user/updateUser.jsp").forward(request, response);
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
