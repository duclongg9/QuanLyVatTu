/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.export_warehouse;

import dao.export.OutputDetailDAO;
import dao.export.OutputWarehourseDAO;
import dao.material.MaterialItemDAO;
import dao.request.requestDAO;
import dao.request.requestDetailDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.RequestDetail;
import model.RequestType;
import model.User;

@WebServlet(name = "CreateExportController", urlPatterns = {"/createExport"})
public class CreateExportController extends HttpServlet {

    requestDetailDAO rddao = new requestDetailDAO();
    OutputWarehourseDAO owdao = new OutputWarehourseDAO();
    OutputDetailDAO oddao = new OutputDetailDAO();
    MaterialItemDAO midao = new MaterialItemDAO();
    requestDAO rdao = new requestDAO();
    
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/exportWarehouse/createExport.jsp").forward(request, response);
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
        String typeParam = request.getParameter("type");
        RequestType type = RequestType.EXPORT;
        if (typeParam != null && !typeParam.isEmpty()) {
            type = RequestType.valueOf(typeParam.toUpperCase());
        }


        int exportId = 0;
        try {
            exportId = owdao.insertOutputWarehouse(loggedInUser.getId(), type);
        } catch (Exception ex) {
            Logger.getLogger(CreateExportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (exportId != 0) {
            List<RequestDetail> materialList = rddao.getAllMaterialsInRequest(requestId);
            for (RequestDetail rd : materialList) {
                try {
                    oddao.insertOutputDetail(exportId, rd.getId(), rd.getMaterialItem().getId(), rd.getQuantity());
                    midao.decreaseQuantityByMaterialItemId(rd.getMaterialItem().getId(), rd.getQuantity());
                } catch (Exception ex) {
                    Logger.getLogger(CreateExportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rdao.updateSuccessStatusRequest(requestId);
            response.sendRedirect("ListExport");
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }
}
