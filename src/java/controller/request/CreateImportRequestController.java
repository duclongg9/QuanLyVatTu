package controller.request;

import dao.Category.CategoryDAO;
import dao.material.MaterialSupplierDAO;
import dao.material.MaterialsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import model.*;

@WebServlet(name = "createRequestImport", urlPatterns = {"/CreateRequestImport"})
public class CreateImportRequestController extends HttpServlet {

    CategoryDAO cmdao = new CategoryDAO();
    MaterialsDAO mdao = new MaterialsDAO();
    MaterialSupplierDAO msdao = new MaterialSupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//
//        List<Category> categoryList = cmdao.getAllCategory();
//        request.setAttribute("categoryList", categoryList);
//
//        HttpSession session = request.getSession();
//        List<SlotItem> slotList = (List<SlotItem>) session.getAttribute("slotList");
//
//        if (slotList == null) {
//            slotList = new ArrayList<>();
//            slotList.add(new SlotItem());
//        }
//
//        // Gán danh sách phụ thuộc cho mỗi slot
//        for (SlotItem slot : slotList) {
//            if (slot.getCategoryId() != null) {
//                slot.setSubCategoryList(scdao.getSubCategoryByCatId(slot.getCategoryId()));
//            }
//
//            if (slot.getSubCategoryId() != null) {
//                try {
//                    slot.setMaterialList(mdao.getMaterialListBySubCateId(slot.getSubCategoryId()));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (slot.getMaterialId() != null) {
//                slot.setSupplierList(msdao.getListSupplierByMaterialId(slot.getMaterialId()));
//            }
//        }
//
//        request.setAttribute("slotList", slotList);
//        request.getRequestDispatcher("/jsp/request/createImportRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

//        if ("addSlot".equals(action)) {
//            List<SlotItem> slotList = new ArrayList<>();
//            int count = Integer.parseInt(request.getParameter("slotCount"));
//
//            for (int i = 0; i < count; i++) {
//                SlotItem slot = new SlotItem();
//                slot.setCategoryId(parseInteger(request.getParameter("categoryId" + i)));
//                slot.setSubCategoryId(parseInteger(request.getParameter("subCategoryId" + i)));
//                slot.setMaterialId(parseInteger(request.getParameter("materialId" + i)));
//                slot.setSupplierId(parseInteger(request.getParameter("supplierId" + i)));
//                slot.setQuantity(parseInteger(request.getParameter("quantity" + i)));
//
//                slotList.add(slot);
//            }
//
//            slotList.add(new SlotItem()); // dòng trống mới
//            session.setAttribute("slotList", slotList);
//            response.sendRedirect("CreateRequestImport");
//            return;
//        }
//
//        if ("cancel".equals(action)) {
//            session.removeAttribute("slotList");
//            response.sendRedirect("requestList");
//        }
    }
    
    private Integer parseInteger(String str) {
        try {
            return (str == null || str.isEmpty()) ? null : Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
