/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package dao;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Map;

/**
 *
 * @author KIET
 */
@WebServlet(name="DashboardServlet", urlPatterns={"/dashboard"})
public class DashboardServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet DashboardServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DashboardServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Connection conn = new DBConnect().getConnection();
        StatisticDAO dao = new StatisticDAO(conn);

        // 1. Tổng quan
        request.setAttribute("totalMaterials", dao.getTotalMaterials());
        request.setAttribute("totalCategories", dao.getTotalCategories());
        request.setAttribute("totalUnits", dao.getTotalUnits());
        request.setAttribute("totalStatuses", dao.getTotalStatuses());

        // 2. Biểu đồ cột: vật tư theo tình trạng
        Map<String, Integer> statusMap = dao.getMaterialCountByStatus();
        request.setAttribute("statusMap", statusMap);

        // 3. Biểu đồ tròn: vật tư theo đơn vị
        Map<String, Integer> unitMap = dao.getMaterialCountByUnit();
        request.setAttribute("unitMap", unitMap);

        // 4. Biểu đồ đường: nhập – xuất theo tháng
        Map<Integer, Integer> inputMap = dao.getMonthlyInput();
        Map<Integer, Integer> outputMap = dao.getMonthlyOutput();
        request.setAttribute("inputMap", inputMap);
        request.setAttribute("outputMap", outputMap);

        // 5. Chuyển sang trang dashboard.jsp
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    System.out.println("Status Map: " + dao.getMaterialCountByStatus());
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
