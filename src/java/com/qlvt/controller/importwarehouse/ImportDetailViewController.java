/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.qlvt.controller.importwarehouse;


import com.qlvt.controller.user.UserListController;
import com.qlvt.dao.imports.InputDetailDAO;
import com.qlvt.dao.imports.InputWarehourseDAO;
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
import com.qlvt.model.InputDetail;
import com.qlvt.model.InputWarehourse;


/**
 *
 * @author D E L L
 */
@WebServlet(name = "ImportDetailViewController", urlPatterns = {"/importDetail"})
public class ImportDetailViewController extends HttpServlet {
    private static final int PAGE_NUMBER = 5;
    
    InputWarehourseDAO iwdao = new InputWarehourseDAO();
    InputDetailDAO iddao = new InputDetailDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String importWarehouseIdParam = request.getParameter("inputWarehouseId");
        if (importWarehouseIdParam == null || importWarehouseIdParam.trim().isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }
        int importWarehouseId = Integer.parseInt(importWarehouseIdParam);
        request.setAttribute("importId",importWarehouseId );
        
        InputWarehourse inputWarehouse = iwdao.getInputWarehourseById(importWarehouseId);
        request.setAttribute("inputWarehouse", inputWarehouse);

        
        //Lấy giá trị trang
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";// load dữ liệu lần đầu tiên cho trang
        }
        int index = Integer.parseInt(indexPage);
        //hiển thị list request
        List<InputDetail> lr = null;
        try {
            lr = iddao.pagingImportDetailByImportWarehouseId(index, importWarehouseId);
        } catch (SQLException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("listImportDetail", lr);

        //Lấy vị trí trang in đậm
        request.setAttribute("tag", index);

        //Lấy tổng số staff trong database
        int count = iddao.getTotalImportDetailByImportWarehouseId(importWarehouseId);
        int endPage = count / PAGE_NUMBER;
        //để nếu chia dư thì 1 trang sẽ có phần tử ít hơn
        if (count % PAGE_NUMBER != 0) {
            endPage++;
        }
        request.setAttribute("endP", endPage);
        request.setAttribute("importWarehouseId", importWarehouseId);

        request.getRequestDispatcher("/jsp/importWarehouse/importDetail.jsp").forward(request, response);
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
