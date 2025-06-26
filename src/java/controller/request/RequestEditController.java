/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.request;

import dao.request.requestDAO;
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
import model.RequestDetail;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "updateRequest", urlPatterns = {"/updateRequest"})
public class RequestEditController extends HttpServlet {

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
        String requestIdStr = request.getParameter("requestId");
        if (requestIdStr != null) {
            int requestId = Integer.parseInt(requestIdStr);
            requestDAO dao = new requestDAO();
            try {
                List<RequestDetail> details = dao.getRequestDetailByRequestId(requestId); 
                request.setAttribute("requestDetails", details);
                request.setAttribute("requestId", requestId);
                request.getRequestDispatcher("/jsp/request/UpdateRequest.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(RequestEditController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("error.jsp"); // thiếu requestId
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] detailIds = request.getParameterValues("detailId");
        String[] quantities = request.getParameterValues("quantity");
        String[] notes = request.getParameterValues("note");

        if (detailIds != null && quantities != null && notes != null) {
            requestDAO dao = new requestDAO();

            for (int i = 0; i < detailIds.length; i++) {
                int detailId = Integer.parseInt(detailIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                String note = notes[i];

                try {
                    dao.updateRequestDetail(detailId, quantity, note);
                } catch (SQLException ex) {
                    Logger.getLogger(RequestEditController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            // Dữ liệu không hợp lệ
            response.sendRedirect("error.jsp");
            return;
        }

        // Sau khi cập nhật xong, redirect về danh sách request
        response.sendRedirect("requestList");
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
