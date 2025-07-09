package controller.request;

import dao.Category.CategoryDAO;
import dao.material.MaterialItemDAO;
import dao.material.MaterialSupplierDAO;
import dao.material.MaterialsDAO;
import dao.request.requestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

@WebServlet(name = "createRequestImport", urlPatterns = {"/CreateRequestImport"})
public class CreateImportRequestController extends HttpServlet {

    CategoryDAO cmdao = new CategoryDAO();
    MaterialsDAO mdao = new MaterialsDAO();
    MaterialItemDAO midao = new MaterialItemDAO();
    MaterialSupplierDAO msdao = new MaterialSupplierDAO();
    requestDAO rdao = new requestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedInUser");
        String userName = user.getFullName();
        List<Category> categoryList = cmdao.getAllCategory();
        request.setAttribute("userName", userName);
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("/jsp/request/createImportRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedInUser"); // đã đăng nhập
        int userId = user.getId();
        String note = request.getParameter("note");
        // 1. Tạo Request mới
        int requestId = 0;
        try {
            requestId = rdao.insertRequest(userId, note, userId, RequestType.IMPORT);
        } catch (SQLException ex) {
            Logger.getLogger(CreateImportRequestController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // 2. Duyệt từng slot được submit
        String[] materialIds = request.getParameterValues("materialId");
        String[] supplierIds = request.getParameterValues("supplierId");
        String[] quantities = request.getParameterValues("quantity");

        for (int i = 0; i < materialIds.length; i++) {
            // Bỏ qua nếu thiếu dữ liệu
            if (materialIds[i] == null || materialIds[i].isEmpty()
                    || supplierIds[i] == null || supplierIds[i].isEmpty()
                    || quantities[i] == null || quantities[i].isEmpty()) {
                continue;
            }

            try {
                int materialId = Integer.parseInt(materialIds[i]);
                int supplierId = Integer.parseInt(supplierIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                // 3. Lấy materials_SupplierId
                int materialSupplierId = msdao.getMaterialSupplier(materialId, supplierId).getId();
                int materialItemId = midao.getMaterialItemById(materialSupplierId).getId();

                // 4. Lưu vào requestDetail
                rdao.insertRequestDetail(requestId, materialItemId, quantity, "Note");
            } catch (NumberFormatException | NullPointerException ex) {
                Logger.getLogger(CreateImportRequestController.class.getName()).log(Level.WARNING, "Invalid input at row " + i, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CreateImportRequestController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.sendRedirect("requestList");
    }

    private Integer parseInteger(String str) {
        try {
            return (str == null || str.isEmpty()) ? null : Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
