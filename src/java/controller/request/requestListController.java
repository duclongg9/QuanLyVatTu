/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.request;

import controller.user.UserListController;

import dao.request.requestDAO;
import dao.request.requestStatusDAO;
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
import model.Request;
import model.RequestStatus;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "requestListController", urlPatterns = {"/requestList"})
public class RequestListController extends HttpServlet {

    public static final int PAGE_NUMBER = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    requestDAO rdao = new requestDAO();
    requestStatusDAO rsdao = new requestStatusDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String statusRaw = request.getParameter("requestStatus");
        String typeRaw = request.getParameter("requestType");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        Integer statusId = (statusRaw != null && !statusRaw.isEmpty() && !"0".equals(statusRaw))
                ? Integer.parseInt(statusRaw)
                : null;

        String type = (typeRaw != null && !typeRaw.isEmpty() && !"0".equals(typeRaw))
                ? typeRaw
                : null;

        List<RequestStatus> listRequestStatus = rsdao.getStatusList();
        request.setAttribute("statusList", listRequestStatus);

        List<String> listRequestType = rsdao.getAllTypeOfRequest();
        request.setAttribute("typeList", listRequestType);

        //Lấy giá trị trang
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";// load dữ liệu lần đầu tiên cho trang
        }
        int index = Integer.parseInt(indexPage);
        //hiển thị list request
        List<Request> lr = null;
        try {
            lr = rdao.pagingRequestWithFilter(index, statusId, type, fromDate, toDate);
        } catch (SQLException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("listRequest", lr);

        //Lấy vị trí trang in đậm
        request.setAttribute("tag", index);

        //Lấy tổng số staff trong database
        int count = rdao.getFilteredTotalRequest(statusId, type, fromDate, toDate);
        int endPage = count / PAGE_NUMBER;
        //để nếu chia dư thì 1 trang sẽ có phần tử ít hơn
        if (count % PAGE_NUMBER != 0) {
            endPage++;
        }
        request.setAttribute("endP", endPage);

        request.getRequestDispatcher("/jsp/request/requestList.jsp").forward(request, response);
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
