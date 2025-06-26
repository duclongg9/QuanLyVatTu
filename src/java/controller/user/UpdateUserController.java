/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dao.role.RoleDAO;
import dao.user.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Role;
import model.User;

/**
 *
 * @author D E L L
 */
@MultipartConfig
@WebServlet(name = "updateUser", urlPatterns = {"/updateUser"})
public class UpdateUserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    UserDAO udao = new UserDAO();
    User u = new User();
    RoleDAO rdao = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Lấy list role
        List<Role> lr = rdao.getAllRole();
        request.setAttribute("listRole", lr);

        //Lấy user theo id
        int uid = Integer.parseInt(request.getParameter("uid"));
        u = udao.getUserById(uid);
        request.setAttribute("user", u);
        request.getRequestDispatcher("/jsp/user/updateUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt((request.getParameter("id")));
            boolean status = Boolean.parseBoolean((request.getParameter("status")));
            int roleId = Integer.parseInt((request.getParameter("roleId")));
            

            // Lấy user hiện tại theo id để kiểm tra role gốc
            User currentUser = udao.getUserById(id);

            
            // Đảm bảo hệ thống chỉ có 1 role duy nhất
            if (roleId == 4 && currentUser.getRole().getId() != 4 && udao.isCEOExit()) {
                
                request.setAttribute("error", "Hệ thống chỉ cho phép một CEO. Không thể gán vai trò CEO cho người này.");

                // Truyền lại dữ liệu user và danh sách role
                request.setAttribute("user", currentUser);
                request.setAttribute("listRole", rdao.getAllRole());
                request.getRequestDispatcher("/jsp/user/updateUser.jsp").forward(request, response);
                return; 
            }

            boolean updated = udao.updateUser(
                    id,
                    status,
                    roleId
            );

            if (updated) {
                response.sendRedirect("userList");
            } else {
                request.setAttribute("error", "Cập nhật user thất bại");
                request.getRequestDispatcher("/jsp/user/updateUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
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
