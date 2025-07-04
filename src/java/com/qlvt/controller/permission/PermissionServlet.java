package com.qlvt.controller.permission;

import com.qlvt.dao.role.PermissionDAO;
import com.qlvt.dao.role.RoleDAO;
import com.qlvt.dao.role.PermissionRoleDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import com.qlvt.model.Permission;
import com.qlvt.model.Role;
import com.qlvt.model.PermissionRole;

@WebServlet(name = "PermissionServlet", urlPatterns = {"/permission"})
public class PermissionServlet extends HttpServlet {

    // Hiển thị giao diện phân quyền (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

         PermissionRoleDAO permissionRoleDAO = new PermissionRoleDAO();
    RoleDAO roleDAO = new RoleDAO();

    List<PermissionRole> permissionList = permissionRoleDAO.getAllPermissionRoles();
    List<Role> roleList = roleDAO.getAllRole();

    Set<String> functionSet = new LinkedHashSet<>();
    Map<String, String> functionUrlMap = new LinkedHashMap<>();
    Map<String, Map<Integer, Boolean>> permissionMap = new LinkedHashMap<>();

    for (PermissionRole pr : permissionList) {
        String function = pr.getPermission().getDescription();
        String url = pr.getPermission().getUrl();
        int roleId = pr.getRole().getId();
        boolean status = pr.isStatus();

        functionSet.add(function);
        functionUrlMap.put(function, url);

        permissionMap.putIfAbsent(function, new LinkedHashMap<>());
        permissionMap.get(function).put(roleId, status);
    }

    // Lấy thông báo nếu có
    HttpSession session = request.getSession();
    String message = (String) session.getAttribute("message");
    if (message != null) {
        request.setAttribute("message", message);
        session.removeAttribute("message"); // xóa sau khi đọc
    }

    request.setAttribute("roles", roleList);
    request.setAttribute("functions", new ArrayList<>(functionSet));
    request.setAttribute("functionUrlMap", functionUrlMap);
    request.setAttribute("permissionMap", permissionMap);

    request.getRequestDispatcher("jsp/permission/permission.jsp").forward(request, response);
    }

    // Lưu thay đổi phân quyền (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PermissionRoleDAO permissionRoleDAO = new PermissionRoleDAO();
        List<PermissionRole> permissionRoles = permissionRoleDAO.getAllPermissionRoles();

        // Danh sách checkbox được chọn
        String[] checkedParams = request.getParameterValues("perm");
        Set<String> checkedSet = new HashSet<>();
        if (checkedParams != null) {
            checkedSet.addAll(Arrays.asList(checkedParams));
        }
        boolean success = true;
        // Duyệt và cập nhật trạng thái
        for (PermissionRole pr : permissionRoles) {
            String key = pr.getPermission().getDescription() + "|" + pr.getRole().getId();
            boolean newStatus = checkedSet.contains(key);
            if (pr.isStatus() != newStatus) {
                permissionRoleDAO.updatePermissionStatus(pr.getId(), newStatus);
            }
        }
        
         HttpSession session = request.getSession();
    if (success) {
        session.setAttribute("message", "Cập nhật phân quyền thành công.");
    } else {
        session.setAttribute("message", "Cập nhật phân quyền thất bại.");
    }


        response.sendRedirect("permission");
    }
}
