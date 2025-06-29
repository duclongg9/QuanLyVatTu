/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.import_warehourse;

import dao.material.MaterialItemDAO;
import dao.request.InputDetailDAO;
import dao.request.InputWarehourseDAO;
import dao.request.requestDAO;
import dao.request.requestDetailDAO;
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
import model.Request;
import model.RequestDetail;
import model.User;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "CreateImportController", urlPatterns = {"/createImport"})
public class CreateImportController extends HttpServlet {

    requestDetailDAO rddao = new requestDetailDAO();
    InputWarehourseDAO iwdao = new InputWarehourseDAO();
    InputDetailDAO iddao = new InputDetailDAO();
    MaterialItemDAO midao = new MaterialItemDAO();
    requestDAO rdao = new requestDAO();

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
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String requestIdParam = request.getParameter("requestId");
        if (requestIdParam == null || requestIdParam.isEmpty()) {
            response.sendRedirect("errorPage.jsp");
            return;
        }
        int requestId = Integer.parseInt(requestIdParam);

        int importId = 0;
        //Tạo đơn nhập kho
        try {
            importId = iwdao.insertInputWarehouse(loggedInUser.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CreateImportController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (importId != 0) {
            List<RequestDetail> materialList = rddao.getAllMaterialsInRequest(requestId);
            for (RequestDetail requestDetail : materialList) {
                try {
                    iddao.insertInputDetail(importId, requestDetail.getId(), requestDetail.getMaterialItem().getId(), requestDetail.getQuantity(), midao.getPriceByMaterialItemId(requestDetail.getMaterialItem().getId()));
                    midao.increaseQuantityByMaterialItemId(requestDetail.getMaterialItem().getId(), requestDetail.getQuantity());
                } catch (SQLException ex) {
                    Logger.getLogger(CreateImportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rdao.updateSuccessStatusRequest(requestId);
            response.sendRedirect("ListImport"); 
            return;
        } else {
            response.sendRedirect("errorPage.jsp");
            return;
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
