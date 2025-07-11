/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.AuditLog;

import dao.auditLog.AuditLogDAO;
import dao.user.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AuditLog;
import model.User;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "AuditLogController", urlPatterns = {"/AuditLog"})
public class AuditLogController extends HttpServlet {

    AuditLogDAO aldao = new AuditLogDAO();
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
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        //phân trang
        int page = 1;
        int pageSize = 10;

        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * pageSize;

        //nhận thông tin
        String tableName = request.getParameter("tableName");
        String actionType = request.getParameter("actionType");
        String changBy = request.getParameter("changedBy");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        List<AuditLog> listAuditLog = null;
        List<String> tableNameList = null;
        int totalPages = 0;
        int totalRecords = 0;
        try {
            totalRecords = aldao.getTotalFilteredAuditLogs(tableName, actionType, changBy, fromDate, toDate);
        } catch (SQLException ex) {
            Logger.getLogger(AuditLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<User> listUser = udao.getAllUser();
        request.setAttribute("userList", listUser);
        try {
            tableNameList = aldao.getAllTableName();
        } catch (SQLException ex) {
            Logger.getLogger(AuditLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            listAuditLog = aldao.getFilteredAuditLogsPaging(tableName, actionType, changBy, fromDate, toDate, offset, pageSize);
        } catch (SQLException ex) {
            Logger.getLogger(AuditLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        totalPages = (totalRecords + pageSize -1)/pageSize;
        request.setAttribute("listAuditLog", listAuditLog);
        request.setAttribute("listTableName", tableNameList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/jsp/auditLog/auditLog.jsp").forward(request, response);
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
