/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.qlvt.controller.supplier;


import com.qlvt.dao.supplier.SupplierDAO;
import com.qlvt.model.Supplier;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author admin
 */
@MultipartConfig
@WebServlet(name = "SupplierController", urlPatterns = {"/suppliercontroller"})
public class SupplierController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        int page = 1;
        int recordsPerPage = 10;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        String status = request.getParameter("status");
        if (status == null) {
            status = "all";
        }

        SupplierDAO dao = new SupplierDAO();
        List<Supplier> list = dao.searchSuppliersByNameAndStatus(keyword, status, (page - 1) * recordsPerPage, recordsPerPage);
        int totalRecords = dao.countSuppliersByNameAndStatus(keyword, status);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        request.setAttribute("listS", list);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);
        request.getRequestDispatcher("jsp/supplier/supplier.jsp").forward(request, response);
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
        doGet(request, response);
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
