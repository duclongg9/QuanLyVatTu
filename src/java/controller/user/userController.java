/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import dao.role.RoleDAO;
import dao.user.UserDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "userListController", urlPatterns = {"/userList"})
public class userController extends HttpServlet {

    public static final int PAGE_NUMBER = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    UserDAO dao = new UserDAO();
    RoleDAO rdao = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Xóa mềm người dùng
        String userId = request.getParameter("id");
        String action = request.getParameter("action");
        if (userId != null && "delete".equals(action)) {
            try {
                int id = Integer.parseInt(userId);
                dao.deleteStaffById(id);
                response.sendRedirect("userList");
                return;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

        List<Role> listRole = rdao.getAllRole();
        request.setAttribute("listRole", listRole);
        // Lấy trang hiện tại 
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        // Lấy roleFilter từ request
        String roleFilter = request.getParameter("roleFilter");
        String searchValue = request.getParameter("search");
        List<User> ls = null;
        int count = 0;

        try {
            if (searchValue != null && !searchValue.trim().isEmpty()) { // ƯU TIÊN SEARCH
                ls = dao.findStaffByName(searchValue, index);
                count = dao.getTotalStaffBySearchName(searchValue);
                request.setAttribute("searchValue", searchValue);
            } else if (roleFilter != null && !roleFilter.isEmpty()) {
                int roleId = Integer.parseInt(roleFilter);
                ls = dao.getUsersByRoleId(roleId, index);
                count = dao.getTotalUserByRoleId(roleId);
                request.setAttribute("roleFilter", roleFilter);
            } else {
                ls = dao.pagingStaff(index);
                count = dao.getTotalStaff();
            }

        } catch (SQLException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("listUser", ls);

        // Xử lý phân trang
        int endPage = count / PAGE_NUMBER;
        if (count % PAGE_NUMBER != 0) {
            endPage++;
        }
        request.setAttribute("endP", endPage);
        request.setAttribute("tag", index);

        request.getRequestDispatcher("/jsp/user/userList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
