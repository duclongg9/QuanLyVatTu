
package controller.request;

import dao.connect.DBConnect;
import dao.request.InputDetailDAO;
import dao.request.InputWarehourseDAO;
import dao.request.requestDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Logger;
import java.util.logging.Level;
import model.InputWarehourse;
import model.RequestDetail;
import model.User;


@WebServlet(name = "createRequestImport", urlPatterns = {"/createImport"})
public class createRequestImport extends HttpServlet {


    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestDAO requestDao = new requestDAO();
        InputWarehourseDAO inputWarehouseDao = new InputWarehourseDAO();
        InputDetailDAO inputDetailDao = new InputDetailDAO();

        int requestId = Integer.parseInt(request.getParameter("requestId"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int userId = user.getId(); // Người thực hiện nhập kho

        Connection conn = null;
        try {
            conn = DBConnect.getConnection();
            conn.setAutoCommit(false);

            // 1. Tạo InputWarehouse mới
            int inputWarehouseId = inputWarehouseDao.insertInputWarehouse(userId);

            // 2. Lấy danh sách RequestDetail của Request này
            List<RequestDetail> requestDetails = requestDao.getRequestDetailByRequestId(requestId);

            // 3. Duyệt từng chi tiết để insert vào InputDetail + update tồn kho
            for (RequestDetail detail : requestDetails) {
                int materialId = detail.getMaterialId().getId();
                int supplierId = detail.getSupplierId().getId();
                int quantity = detail.getQuantity();

                // Lấy giá nhập từ MaterialItem
                double inputPrice = requestDao.getPriceForMaterialItem(materialId, supplierId);

                // Insert vào InputDetail
                inputDetailDao.insertInputDetail(inputWarehouseId, detail.getId(), materialId, supplierId, quantity, inputPrice);

                // Update stock quantity trong MaterialItem
                requestDao.updateStockQuantity(materialId, supplierId, quantity);
            }

            conn.commit();
            response.sendRedirect("ListImport"); // về trang danh sách nhập kho

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
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public String getServletInfo() {
        return "Create Import Warehouse from Request";
    }
}
