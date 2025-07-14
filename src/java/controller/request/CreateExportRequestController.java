/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.request;

import dao.Category.CategoryDAO;
import dao.connect.DBConnect;
import dao.material.MaterialItemDAO;
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
import java.util.HashMap;
import java.util.Map;
import model.Category;
import model.MaterialItem;
import model.Category;
import model.User;
import model.RequestType;

@WebServlet(name = "createRequestExport", urlPatterns = {"/CreateRequestExport"})
public class CreateExportRequestController extends HttpServlet {

    CategoryDAO cmdao = new CategoryDAO();
    CategoryDAO cdao = new CategoryDAO();
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

        List<Category> cateList = cmdao.getAllCategory();
        List<Category> subList = cdao.getSubCategoryByCatId(0);
        List<MaterialItem> miList = midao.filterMaterial(categoryMaterialId, subCategoryId, keyword);

        HttpSession session = request.getSession();
        Map<Integer, Integer> selectedItems = (Map<Integer, Integer>) session.getAttribute("selectedExportItems");
        if (selectedItems != null) {
            for (MaterialItem mi : miList) {
                if (selectedItems.containsKey(mi.getId())) {
                    mi.setSelectedQuantity(selectedItems.get(mi.getId()));
                }
            }
        }

        request.setAttribute("categoryList", cateList);
        request.setAttribute("subCategoryList", subList);
        request.setAttribute("materialItemList", miList);

        request.getRequestDispatcher("/jsp/request/createExportRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        requestDAO requestDao = new requestDAO();
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        User user = (User) session.getAttribute("account");

        if ("add".equals(action)) {
            String[] materialItemIds = request.getParameterValues("materialItemIds");
            Map<Integer, Integer> selectedItems = (Map<Integer, Integer>) session.getAttribute("selectedExportItems");
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
                            selectedItems.put(itemId, qty);
                        }
                    }
                }
            }

            session.setAttribute("selectedExportItems", selectedItems);
            response.sendRedirect("CreateRequestExport");
            return;
        }

        if ("create".equals(action)) {
            String note = request.getParameter("note");
            Map<Integer, Integer> selectedItems = (Map<Integer, Integer>) session.getAttribute("selectedExportItems");

            Connection conn = null;
            try {
                conn = DBConnect.getConnection();
                conn.setAutoCommit(false);

                int requestId = requestDao.insertRequest( user.getId(), note, null, RequestType.EXPORT,user.getId());

                if (selectedItems != null && !selectedItems.isEmpty()) {
                    for (Map.Entry<Integer, Integer> entry : selectedItems.entrySet()) {
                        int materialItemId = entry.getKey();
                        int quantity = entry.getValue();
                        requestDao.insertRequestDetail( requestId, materialItemId, quantity, null);
                    }
                }

                conn.commit();
                session.removeAttribute("selectedExportItems");
                response.sendRedirect("requestList");

            } catch (Exception e) {
                e.printStackTrace();
                if (conn != null) {
                    try {
                        conn.rollback();
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
            session.removeAttribute("selectedExportItems");
            response.sendRedirect("requestList");
            return;
        }

    }

    @Override
    public String getServletInfo() {
        return "Create Export Warehouse Request";
    }
}