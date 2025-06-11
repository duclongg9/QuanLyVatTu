package controller;

import dao.Roledao;
import dao.Userdao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Role;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UpdateUser", urlPatterns = {"/UpdateUser"})
public class UpdateUserServlet extends HttpServlet {

    private final Userdao udao = new Userdao();
    private final Roledao rdao = new Roledao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));
        User user = udao.getUserById(uid);
        List<Role> roles = rdao.getAllRole();
        request.setAttribute("user", user);
        request.setAttribute("listRole", roles);
        request.getRequestDispatcher("Admin/updateUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int uid = Integer.parseInt(request.getParameter("id"));
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            boolean status = Boolean.parseBoolean(request.getParameter("status"));

            boolean updated = udao.updateUserStatusAndRole(uid, roleId, status);
            if (updated) {
                response.sendRedirect("userList");
            } else {
                request.setAttribute("error", "Cập nhật user thất bại");
                request.getRequestDispatcher("Admin/updateUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            request.getRequestDispatcher("Admin/updateUser.jsp").forward(request, response);
        }
    }
}