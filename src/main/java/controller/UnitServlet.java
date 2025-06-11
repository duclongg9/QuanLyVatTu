/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.UnitDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Unit;

/**
 *
 * @author KIET
 */
@WebServlet(name = "UnitServlet", urlPatterns = {"/unit"})
public class UnitServlet extends HttpServlet {

    private UnitDAO unitDAO = new UnitDAO();

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
            out.println("<title>Servlet UnitServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UnitServlet at " + request.getContextPath() + "</h1>");
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
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                request.getRequestDispatcher("unitForm.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Unit u = unitDAO.getUnitById(id);
                request.setAttribute("unit", u);
                request.getRequestDispatcher("unitForm.jsp").forward(request, response);
                break;
            case "delete":
                unitDAO.deleteUnit(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("unit");
                break;
            case "confirmDelete":
                int idToConfirm = Integer.parseInt(request.getParameter("id"));
                Unit unitToConfirm = unitDAO.getUnitById(idToConfirm);
                request.setAttribute("unit", unitToConfirm);
                request.getRequestDispatcher("unitConfirmDelete.jsp").forward(request, response);
                break;
            default: // list
                List<Unit> list = unitDAO.getAllUnits();
                request.setAttribute("list", list);
                request.getRequestDispatcher("unitList.jsp").forward(request, response);
                break;
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
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");

        if (idStr == null || idStr.isEmpty()) {
            unitDAO.addUnit(name);
        } else {
            int id = Integer.parseInt(idStr);
            unitDAO.updateUnit(id, name);
        }
        response.sendRedirect("unit"); // đúng tên servlet
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
