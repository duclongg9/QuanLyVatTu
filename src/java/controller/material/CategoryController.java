/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.material;

import dao.Category.CategoryDAO;
import dao.material.MaterialsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import java.io.IOException;
import java.util.List;
import model.Materials;
/**
 *
 * @author Dell-PC
 */@WebServlet(name = "CategoryController", urlPatterns = {"/categoryController"})
public class CategoryController extends HttpServlet {

    CategoryDAO dao = new CategoryDAO();
    CategoryDAO subDao = new CategoryDAO();
    MaterialsDAO materialsDAO = new MaterialsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                request.getRequestDispatcher("/jsp/material/createCategory.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("category", dao.getCategoryById(id));
                request.getRequestDispatcher("/jsp/material/createCategory.jsp").forward(request, response);
                break;
            case "deleted":
                String delIndexPage = request.getParameter("index");
                if (delIndexPage == null) delIndexPage = "1";
                int delIndex = Integer.parseInt(delIndexPage);
                request.setAttribute("categories", dao.pagingDeletedCategories(delIndex));
                int totalDel = dao.getTotalDeletedCategories();
                int endDel = totalDel / CategoryDAO.PAGE_SIZE;
                if (totalDel % CategoryDAO.PAGE_SIZE != 0) endDel++;
                request.setAttribute("endP", endDel);
                request.setAttribute("tag", delIndex);
                request.getRequestDispatcher("/jsp/material/deletedCategory.jsp").forward(request, response);
                break;
            case "activate":
                int aId = Integer.parseInt(request.getParameter("id"));
                dao.activateCategory(aId);
                response.sendRedirect("categoryController?action=deleted");
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                if (subDao.getTotalSubCategoryByCategory(delId) > 0 || materialsDAO.getTotalMaterialsByCategory(delId) > 0) {
                    request.setAttribute("error", "Không thể xóa danh mục do còn vật tư phụ thuộc");
                    String indexPage = request.getParameter("index");
                    if (indexPage == null) indexPage = "1";
                    int index = Integer.parseInt(indexPage);
                    String search = request.getParameter("search");
                    int total;
                    if (search != null && !search.trim().isEmpty()) {
                        request.setAttribute("categories", dao.searchCategoriesByName(search, index));
                        total = dao.getTotalCategoriesByName(search);
                        request.setAttribute("searchValue", search);
                    } else {
                        request.setAttribute("categories", dao.pagingCategories(index));
                        total = dao.getTotalCategories();
                    }
                    int endP = total / CategoryDAO.PAGE_SIZE;
                    if (total % CategoryDAO.PAGE_SIZE != 0) endP++;
                    request.setAttribute("endP", endP);
                    request.setAttribute("tag", index);
                    request.getRequestDispatcher("/jsp/material/listCategory.jsp").forward(request, response);
                } else {
                    int result = dao.deleteCategory(delId);
                    if (result > 0) {
                        subDao.deactivateByCategoryId(delId);
                        response.sendRedirect("categoryController");
                    } else {
                        request.setAttribute("error", "Xử lý thất bại");
//                        doGet(request, response);
                    }
                }
                break;
            default:
                 String indexPage = request.getParameter("index");
                if (indexPage == null) {
                    indexPage = "1";
                }
                int index = Integer.parseInt(indexPage);
                String search = request.getParameter("search");
                int total;
                if (search != null && !search.trim().isEmpty()) {
                    request.setAttribute("categories", dao.searchCategoriesByName(search, index));
                    total = dao.getTotalCategoriesByName(search);
                    request.setAttribute("searchValue", search);
                } else {
                    request.setAttribute("categories", dao.pagingCategories(index));
                    total = dao.getTotalCategories();
                }
                int endP = total / CategoryDAO.PAGE_SIZE;
                if (total % CategoryDAO.PAGE_SIZE != 0) {
                    endP++;
                }
                request.setAttribute("endP", endP);
                request.setAttribute("tag", index);
                request.getRequestDispatcher("/jsp/material/listCategory.jsp").forward(request, response);
        }
       
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String category = request.getParameter("category");
        if (!isValidName(category)) {
            request.setAttribute("error", "Tên danh mục không hợp lệ");
            request.getRequestDispatcher("/jsp/material/createCategory.jsp").forward(request, response);
            return;
        }

        Integer id = null;
        if (idParam != null && !idParam.isEmpty()) {
            id = Integer.parseInt(idParam);
        }

        if (dao.isNameExists(category, id)) {
            request.setAttribute("error", "Tên danh mục đã tồn tại");
            request.getRequestDispatcher("/jsp/material/createCategory.jsp").forward(request, response);
            return;
        }

        int result;
        if (id != null) {
            result = dao.updateCategory(id, category);
        } else {
            result = dao.createCategory(category);
        }
        if (result > 0) {
            response.sendRedirect("categoryController");
        } else {
            request.setAttribute("error", "Xử lý thất bại");
            request.getRequestDispatcher("/jsp/material/createCategory.jsp").forward(request, response);
        }
    }
    
    private boolean isValidName(String name) {
        if (name == null || name.length() > 50) {
            return false;
        }
        return !name.contains("  ");
    }
}
