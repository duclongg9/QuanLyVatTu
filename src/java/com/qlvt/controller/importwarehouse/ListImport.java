/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.qlvt.controller.request;

import com.qlvt.dao.request.InputWarehourseDAO;
import com.qlvt.dao.request.requestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qlvt.model.InputWarehourse;
import com.qlvt.model.RequestDetail;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "ListImport", urlPatterns = {"/ListImport"})
public class ListImport extends HttpServlet {

    InputWarehourseDAO dao = new InputWarehourseDAO();
    InputWarehourse idao = new InputWarehourse();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListImport</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListImport at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<InputWarehourse> list = dao.getAllInputWarehouses();
            request.setAttribute("inputWarehouses", list);
            request.getRequestDispatcher("/jsp/importWarehouse/Import.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(CreateImportRequestController.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
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
