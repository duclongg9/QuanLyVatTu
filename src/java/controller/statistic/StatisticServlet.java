/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.statistic;

import dao.connect.DBConnect;
import dao.statistic.StatisticDAO;
import model.Statistic;
import model.Category;
import model.User;
import dao.material.CategoryDAO;
import dao.user.UserDAO;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author KIET
 */
@WebServlet(name = "StatisticServlet", urlPatterns = {"/statistic"})
public class StatisticServlet extends HttpServlet {

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
            out.println("<title>Servlet StatisticServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StatisticServlet at " + request.getContextPath() + "</h1>");
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
        String action = request.getParameter("action");
        if ("import".equals(action)) {
    handleImportStatistic(request, response);
} else if ("remain".equals(action)) {
    handleRemainStatistic(request, response);
} else if ("dashboard".equals(action)) {
     handleDashboard(request, response);
} else {
    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy chức năng.");
}
    }
    
    private void handleDashboard(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try (Connection conn = DBConnect.getConnection()) {
        StatisticDAO dao = new StatisticDAO(conn);

        int totalMaterials = dao.getTotalMaterials();
        int totalCategories = dao.getTotalCategories();
        int totalUnits = dao.getTotalUnits();
        int totalStatuses = dao.getTotalStatuses();

        Map<String, Integer> statusMap = dao.getMaterialCountByStatus();
        Map<String, Integer> unitMap = dao.getMaterialCountByUnit();
        Map<Integer, Integer> inputMap = dao.getMonthlyInput();
        Map<Integer, Integer> outputMap = dao.getMonthlyOutput();

        request.setAttribute("totalMaterials", totalMaterials);
        request.setAttribute("totalCategories", totalCategories);
        request.setAttribute("totalUnits", totalUnits);
        request.setAttribute("totalStatuses", totalStatuses);

        request.setAttribute("statusMap", statusMap);
        request.setAttribute("unitMap", unitMap);
        request.setAttribute("inputMap", inputMap);
        request.setAttribute("outputMap", outputMap);

        request.getRequestDispatcher("jsp/statistic/statisticsDashboard.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi load dashboard.");
    }
}




    private void handleImportStatistic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {

            String fromRaw = request.getParameter("from");
            String toRaw = request.getParameter("to");

            Date from = Date.valueOf(fromRaw);
            Date to = Date.valueOf(toRaw);

            conn = new DBConnect().getConnection();
            StatisticDAO dao = new StatisticDAO(conn);
            List<Statistic> mainList = dao.getImportStatistic(from, to);
            List<Statistic> importByCategory = dao.getImportByCategory(from, to);
            List<Statistic> importByDate = dao.getImportByDate(from, to);

            request.setAttribute("statisticList", mainList);
            request.setAttribute("importByCategory", importByCategory);
            request.setAttribute("importByDate", importByDate);

            request.getRequestDispatcher("jsp/statistic/statisticImport.jsp").forward(request, response);

            request.getRequestDispatcher("/jsp/statistic/statisticImport.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi thống kê nhập kho.");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    private void handleRemainStatistic(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = new DBConnect().getConnection()) {
            StatisticDAO dao = new StatisticDAO(conn);
            List<Map<String, Object>> remainStats = dao.getRemainByCategoryAndStatus();

            request.setAttribute("remainStats", remainStats);
            request.getRequestDispatcher("/jsp/statistic/statisticRemain.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi thống kê tồn kho.");
        }
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
        String action = request.getParameter("action");
        if ("export".equals(action)) {
            handleExportStatistic(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy chức năng.");
        }
    }

    private void handleExportStatistic(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        Date from = Date.valueOf(request.getParameter("fromDate"));
        Date to = Date.valueOf(request.getParameter("toDate"));

        int categoryId = parseIntOrDefault(request.getParameter("categoryId"), 0);
        int userId = parseIntOrDefault(request.getParameter("userId"), 0);

        Connection conn = new DBConnect().getConnection();
        StatisticDAO dao = new StatisticDAO(conn);

        
        List<Statistic> list = dao.getExportStatistic(from, to, categoryId, userId);
        
        String message;
if (list == null || list.isEmpty()) {
    message = "Không tìm thấy dữ liệu xuất kho trong khoảng thời gian đã chọn.";
} else {
    message = "Đã tìm thấy " + list.size() + " vật tư đã xuất trong khoảng thời gian đã chọn.";
}

request.setAttribute("message", message);
        
        CategoryDAO categoryDAO = new CategoryDAO();
        UserDAO userDAO = new UserDAO();
        List<Category> categoryList = categoryDAO.getAllCategory();
        List<User> userList = userDAO.getListUser();

        
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("userList", userList);
        request.setAttribute("exportStats", list);
        request.getRequestDispatcher("jsp/statistic/statisticExport.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi thống kê.");
    }
}


// ham nay de tranh loi number format 
    private int parseIntOrDefault(String input, int defaultValue) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return defaultValue;
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
