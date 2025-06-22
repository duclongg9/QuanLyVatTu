/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.sql.Connection;
import static controller.requestListController.PAGE_NUMBER;
import dao.DBConnect;
import dao.MaterialsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Materials;
import dao.requestDAO;

/**
 *
 * @author D E L L
 */
@WebServlet(name = "createRequestImportController", urlPatterns = {"/CreateRequestImport"})
public class createRequestImportController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    MaterialsDAO mdao = new MaterialsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Lấy giá trị trang
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";// load dữ liệu lần đầu tiên cho trang
        }
        int index = Integer.parseInt(indexPage);
        //hiển thị list requesterialst
        List<Materials> lm = null;
        try {
            lm = mdao.pagingMaterials(index);
        } catch (SQLException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("listMaterialRequest", lm);
        HttpSession session = request.getSession();
        session.setAttribute("listMaterialRequest", lm); // Cho doPost

        //Lấy vị trí trang in đậm
        request.setAttribute("tag", index);

        //Lấy tổng số staff trong database
        int count = mdao.getTotalMaterials();
        int endPage = count / PAGE_NUMBER;
        //để nếu chia dư thì 1 trang sẽ có phần tử ít hơn
        if (count % PAGE_NUMBER != 0) {
            endPage++;
        }
        request.setAttribute("endP", endPage);

        request.getRequestDispatcher("createImportRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String note = request.getParameter("note");
        HttpSession session = request.getSession();
//        int userId = (Integer) session.getAttribute("userId");
        int userId = 1;
        Integer approverId = null; // có thể lấy từ session hoặc để null

        List<Materials> listMaterial = (List<Materials>) session.getAttribute("listMaterialRequest");
        Map<Integer, Integer> materialQuantityMap = new HashMap<>();

        // Lấy dữ liệu quantity từ request gửi về
        for (Materials m : listMaterial) {
            String param = request.getParameter("material_" + m.getId());
            if (param != null && !param.isEmpty()) {
                int quantity = Integer.parseInt(param);
                if (quantity > 0) {
                    materialQuantityMap.put(m.getId(), quantity);
                }
            }
        }

        Connection conn = null;
        try {
            conn = DBConnect.getConnection();
            conn.setAutoCommit(false); // bắt đầu transaction

            requestDAO requestDAO = new requestDAO();
            int requestId = requestDAO.insertRequest(conn, userId, note, approverId);

            if (requestId > 0) {
                requestDAO.insertRequestDetails(conn, requestId, materialQuantityMap);
            }

            conn.commit(); // OK hết thì commit
            response.sendRedirect("requestList");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
