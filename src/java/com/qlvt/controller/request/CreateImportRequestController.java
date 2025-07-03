package com.qlvt.controller.request;

import com.qlvt.dao.material.CategoryMaterialDAO;
import com.qlvt.dao.connect.DBConnect;
import com.qlvt.dao.material.MaterialItemDAO;
import com.qlvt.dao.request.InputDetailDAO;
import com.qlvt.dao.request.requestDAO;
import com.qlvt.dao.subcategory.SubCategoryDAO;
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
import java.util.HashMap;
import java.util.Map;
import com.qlvt.model.CategoryMaterial;
import com.qlvt.model.MaterialItem;
import com.qlvt.model.SubCategory;
import com.qlvt.model.User;
import com.qlvt.model.RequestType;

@WebServlet(name = "createRequestImport", urlPatterns = {"/CreateRequestImport"})
public class CreateImportRequestController extends HttpServlet {

    CategoryMaterialDAO cmdao = new CategoryMaterialDAO();
    SubCategoryDAO scdao = new SubCategoryDAO();
    MaterialItemDAO midao = new MaterialItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryMaterialIdParam = request.getParameter("categoryMaterialId");
        String subCategoryIdParam = request.getParameter("subCategoryId");
        String keyword = request.getParameter("keyword");
        int categoryMaterialId;
        int subCategoryId;

        if (keyword == null) {
            keyword = "";
        }

        if (subCategoryIdParam != null) {
            subCategoryId = Integer.parseInt(subCategoryIdParam);
        } else {
            subCategoryId = 0;
        }

        if (categoryMaterialIdParam != null) {
            categoryMaterialId = Integer.parseInt(categoryMaterialIdParam);
        } else {
            categoryMaterialId = 0;
        }

        List<CategoryMaterial> cateList = cmdao.getAllCategory();
        List<SubCategory> subList = scdao.getSubCategoryByCatId(0);
        List<MaterialItem> miList = midao.filterMaterial(categoryMaterialId, subCategoryId, keyword);

        // Gán số lượng mà User đã chọn từ session 
        HttpSession session = request.getSession();
        Map<Integer, Integer> selectedItems = (Map<Integer, Integer>) session.getAttribute("selectedItems");
        if (selectedItems != null) {
            for (MaterialItem mi : miList) {
                if (selectedItems.containsKey(mi.getId())) {
                    mi.setSelectedQuantity(selectedItems.get(mi.getId())); // biến tạm Java, không nằm trong DB
                }
            }
        }

        request.setAttribute("categoryList", cateList);
        request.setAttribute("subCategoryList", subList);
        request.setAttribute("materialItemList", miList);

        request.getRequestDispatcher("/jsp/request/createImportRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        requestDAO requestDao = new requestDAO();
        InputDetailDAO inputDetailDao = new InputDetailDAO();

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        User user = (User) session.getAttribute("account");

        if ("add".equals(action)) {
            String[] materialItemIds = request.getParameterValues("materialItemIds");
            Map<Integer, Integer> selectedItems = (Map<Integer, Integer>) session.getAttribute("selectedItems");
            if (selectedItems == null) {
                selectedItems = new HashMap<>();
            }

            if (materialItemIds != null) {
                for (String idStr : materialItemIds) {
                    int itemId = Integer.parseInt(idStr);
                    String quantityStr = request.getParameter("quantities[" + itemId + "]");
                    if (quantityStr != null && !quantityStr.isEmpty()) {
                        int qty = Integer.parseInt(quantityStr);
                        if (qty > 0) {
                            selectedItems.put(itemId, qty); // Ghi đè nếu đã có(người dùng chỉnh lại phần số lượng khi chưa lưu về database)
                        }
                    }
                }
            }

            session.setAttribute("selectedItems", selectedItems);
            response.sendRedirect("CreateRequestImport"); // Load lại với dữ liệu mới
            return;
        }

        if ("create".equals(action)) {
            String note = request.getParameter("note");
            Map<Integer, Integer> selectedItems = (Map<Integer, Integer>) session.getAttribute("selectedItems");

            Connection conn = null;
            try {
                conn = DBConnect.getConnection();
                conn.setAutoCommit(false);

                int requestId = requestDao.insertRequest(conn, user.getId(), note, null, RequestType.IMPORT);

                if (selectedItems != null && !selectedItems.isEmpty()) {
                    for (Map.Entry<Integer, Integer> entry : selectedItems.entrySet()) {
                        int materialItemId = entry.getKey();
                        int quantity = entry.getValue();
                        requestDao.insertRequestDetail(conn, requestId, materialItemId, quantity, null);
                    }
                }

                conn.commit(); // thành công
                session.removeAttribute("selectedItems");
                response.sendRedirect("requestList");

            } catch (Exception e) {
                e.printStackTrace();
                if (conn != null) {
                    try {
                        conn.rollback();//fail
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                response.sendRedirect("errorPage.jsp");
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        if ("cancel".equals(action)) {
            session.removeAttribute("selectedItems"); // Xóa phần làm dở
            response.sendRedirect("requestList"); // Chuyển hướng về danh sách yêu cầu
            return;
        }

    }

    @Override
    public String getServletInfo() {
        return "Create Import Warehouse from Request";
    }
}
