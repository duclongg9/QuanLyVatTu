/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.MaterialsDAO;
import dao.UnitConversionDAO;
import dao.UnitDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Materials;
import model.Unit;
import model.UnitConversion;

/**
 *
 * @author KIET
 */
@WebServlet(name = "UnitConversionServlet", urlPatterns = {"/unitConversion"})
public class UnitConversionServlet extends HttpServlet {

    private UnitConversionDAO unitConversionDAO = new UnitConversionDAO();
    private UnitDAO unitDAO = new UnitDAO();
    private MaterialsDAO materialsDAO = new MaterialsDAO();

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
            out.println("<title>Servlet UnitConversionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UnitConversionServlet at " + request.getContextPath() + "</h1>");
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
        List<Unit> units = new UnitDAO().getAllUnits();
        List<Materials> materials = materialsDAO.getAllMaterial();

        switch (action) {
            case "create":
                // Chuẩn bị dữ liệu

                request.setAttribute("units", units);
                request.setAttribute("materials", materials);
                request.getRequestDispatcher("unitConversionForm.jsp").forward(request, response);
                break;

            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                UnitConversion uc = unitConversionDAO.getById(id);
                if (uc == null) {
        response.sendRedirect("unitConversion?error=notfound");
        return;
    }
                request.setAttribute("unitConversion", uc);  // ⚠️ KEY này phải đúng với trong JSP
                request.setAttribute("units", units);
                request.setAttribute("materials", materials);

                request.getRequestDispatcher("unitConversionForm.jsp").forward(request, response);
                break;

            case "confirmDelete":
                int delId = Integer.parseInt(request.getParameter("id"));
                UnitConversion ucToDelete = unitConversionDAO.getById(delId);
                request.setAttribute("uc", ucToDelete);
                request.setAttribute("material", materialsDAO.getMaterialById(ucToDelete.getMaterialId()));
                request.setAttribute("fromUnit", unitDAO.getUnitById(ucToDelete.getFromUnitId()));
                request.setAttribute("toUnit", unitDAO.getUnitById(ucToDelete.getToUnitId()));
                request.getRequestDispatcher("unitConversionConfirmDelete.jsp").forward(request, response);
                break;

            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                unitConversionDAO.delete(deleteId);
                response.sendRedirect("unitConversion");
                break;

            default: // list
                List<UnitConversion> list = unitConversionDAO.getAllUnitConversion();
                request.setAttribute("unitConversionList", list);
                request.getRequestDispatcher("unitConversionList.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String action = request.getParameter("action");

    if ("create".equals(action)) {
        int materialId = Integer.parseInt(request.getParameter("materialId"));
        int fromUnitId = Integer.parseInt(request.getParameter("fromUnitId"));
        int toUnitId = Integer.parseInt(request.getParameter("toUnitId"));
        double ratio = Double.parseDouble(request.getParameter("ratio"));

        UnitConversion uc = new UnitConversion();
        uc.setMaterialId(materialId);
        uc.setFromUnitId(fromUnitId);
        uc.setToUnitId(toUnitId);
        uc.setRatio(ratio);

        new UnitConversionDAO().insert(uc);
        response.sendRedirect("unitConversion");

    } else if ("update".equals(action)) {
        int id = Integer.parseInt(request.getParameter("id"));
        int materialId = Integer.parseInt(request.getParameter("materialId"));
        int fromUnitId = Integer.parseInt(request.getParameter("fromUnitId"));
        int toUnitId = Integer.parseInt(request.getParameter("toUnitId"));
        double ratio = Double.parseDouble(request.getParameter("ratio"));

        UnitConversion uc = new UnitConversion();
        uc.setId(id);
        uc.setMaterialId(materialId);
        uc.setFromUnitId(fromUnitId);
        uc.setToUnitId(toUnitId);
        uc.setRatio(ratio);

        new UnitConversionDAO().update(uc);
        response.sendRedirect("unitConversion");
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
