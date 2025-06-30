package controller.request;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import controller.user.UserListController;
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
@WebServlet(urlPatterns = {"/requestDetailController"})
public class RequestDetailViewController extends HttpServlet {

    public static final int PAGE_NUMBER = 5;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    requestDetailDAO rddao = new requestDetailDAO();
    requestDAO rdao = new requestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        Request userRequest = rdao.getRequestById(requestId);
        request.setAttribute("userRequest", userRequest);
        //Lấy giá trị trang
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";// load dữ liệu lần đầu tiên cho trang
        }
        int index = Integer.parseInt(indexPage);
        //hiển thị list request
        List<RequestDetail> lr = null;
        try {
            lr = rddao.pagingRequestDetailByRequestId(index, requestId);
        } catch (SQLException ex) {
            Logger.getLogger(UserListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("listRequestDetail", lr);

        //Lấy vị trí trang in đậm
        request.setAttribute("tag", index);

        //Lấy tổng số staff trong database
        int count = rddao.getTotalRequestDetailByRequestId(requestId);
        int endPage = count / PAGE_NUMBER;
        //để nếu chia dư thì 1 trang sẽ có phần tử ít hơn
        if (count % PAGE_NUMBER != 0) {
            endPage++;
        }
        request.setAttribute("endP", endPage);
        request.setAttribute("requestId", requestId);

        request.getRequestDispatcher("/jsp/request/requestDetail.jsp").forward(request, response);
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
         int userId = loggedInUser.getId();
        
        String requestIdParam = request.getParameter("requestId");
        if (requestIdParam == null || requestIdParam.isEmpty()) {
            response.sendRedirect("errorPage.jsp"); 
            return;
        }
        String newStatusId = request.getParameter("newStatusId");

        if (newStatusId != null) {
            int statusId = Integer.parseInt(newStatusId);
            int requestId = Integer.parseInt(requestIdParam);
           

            rdao.updateStatusRequest(requestId, statusId,userId);
            response.sendRedirect("requestDetail?requestId=" + requestId); 
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
