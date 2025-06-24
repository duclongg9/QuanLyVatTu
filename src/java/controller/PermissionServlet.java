package controller;

import dao.PermissionDAO;
import dao.RoleDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.Permission;
import model.Role;

@WebServlet(name = "PermissionServlet", urlPatterns = {"/permission"})
public class PermissionServlet extends HttpServlet {

    // Hiển thị giao diện phân quyền (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PermissionDAO permissionDAO = new PermissionDAO();
        RoleDAO roleDAO = new RoleDAO();

        List<Permission> permissionList = permissionDAO.getAllPermissions();
        List<Role> roleList = roleDAO.getAllRole();

        // Dùng LinkedHashSet để giữ thứ tự
        Set<String> functionSet = new LinkedHashSet<>();
        Map<String, String> functionUrlMap = new LinkedHashMap<>();
        Map<String, Map<Integer, Integer>> permissionMap = new LinkedHashMap<>();

        for (Permission p : permissionList) {
            String function = p.getFunction();
            String url = p.getUrl();
            int roleId = p.getRole().getId();
            int status = p.getStatus();

            functionSet.add(function);
            functionUrlMap.put(function, url);

            permissionMap.putIfAbsent(function, new LinkedHashMap<>());
            permissionMap.get(function).put(roleId, status);
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("roles", roleList);
        request.setAttribute("functions", new ArrayList<>(functionSet));
        request.setAttribute("functionUrlMap", functionUrlMap);
        request.setAttribute("permissionMap", permissionMap);

        request.getRequestDispatcher("permission.jsp").forward(request, response);
    }

    // Lưu thay đổi phân quyền (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PermissionDAO permissionDAO = new PermissionDAO();
        List<Permission> allPermissions = permissionDAO.getAllPermissions();

        // Các checkbox được chọn
        String[] checkedParams = request.getParameterValues("perm");
        Set<String> checkedSet = new HashSet<>();
        if (checkedParams != null) {
            checkedSet.addAll(Arrays.asList(checkedParams));
        }

        // Cập nhật nếu có sự thay đổi
        for (Permission p : allPermissions) {
            String key = p.getFunction() + "|" + p.getRole().getId();
            int newStatus = checkedSet.contains(key) ? 1 : 0;

            if (p.getStatus() != newStatus) {
                permissionDAO.updatePermissionStatus(p.getId(), newStatus);
            }
        }

        response.sendRedirect("permission");
    }
}
