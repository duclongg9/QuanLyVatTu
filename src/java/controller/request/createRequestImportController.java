/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.request;

import static controller.material.MaterialController.PAGE_NUMBER;
import java.sql.Connection;
import controller.user.userController;
import dao.Supplier.SupplierDAO;
import dao.connect.DBConnect;
import dao.material.MaterialsDAO;
import dao.request.requestDAO;
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
import dao.material.MaterialSupplierDAO;
import model.MaterialSupplier;
import model.User;

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
    MaterialSupplierDAO msdao = new MaterialSupplierDAO();
    MaterialsDAO mdao = new MaterialsDAO();
    SupplierDAO sdao = new SupplierDAO();
    requestDAO rdao = new requestDAO();

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
        List<MaterialSupplier> lm = null;
        try {
            lm = msdao.pagingMaterialWithSupplier(index);
        } catch (SQLException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("listMaterialRequest", lm);
        HttpSession session = request.getSession();
        session.setAttribute("listMaterialRequest", lm); // Cho doPost

        //Lấy vị trí trang in đậm
        request.setAttribute("tag", index);

        int count = msdao.getTotalMaterialWithSupplier();
        int endPage = count / PAGE_NUMBER;
        //để nếu chia dư thì 1 trang sẽ có phần tử ít hơn
        if (count % PAGE_NUMBER != 0) {
            endPage++;
        }
        request.setAttribute("endP", endPage);

        request.getRequestDispatcher("/jsp/request/createImportRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String note = request.getParameter("note");
        HttpSession session = request.getSession(false); // lấy session hiện tại

        User user = (User) session.getAttribute("account"); // lấy user từ session
        int userId = user.getId(); // dùng ID thật thay vì giả lập
        Integer approverId = null; // chưa duyệt

        Connection conn = null;
        try {
            // Lấy mảng các tham số từ form
            String[] materialIds = request.getParameterValues("materialId");
            String[] supplierIds = request.getParameterValues("supplierId");
            String[] quantities = request.getParameterValues("quantity");

            conn = DBConnect.getConnection();
            conn.setAutoCommit(false); // transaction

            requestDAO requestDAO = new requestDAO();
            int requestId = requestDAO.insertRequest(conn, userId, note, approverId);

            if (requestId > 0 && materialIds != null && supplierIds != null && quantities != null) {
                for (int i = 0; i < materialIds.length; i++) {
                    int materialId = Integer.parseInt(materialIds[i]);
                    int supplierId = Integer.parseInt(supplierIds[i]);
                    int quantity = Integer.parseInt(quantities[i]);

                    if (quantity > 0) {
                        requestDAO.insertRequestDetail(conn, requestId, materialId, supplierId, quantity, null);
                    }
                }
            }

            conn.commit(); // OK thì commit
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
            response.sendRedirect("requestList");
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
