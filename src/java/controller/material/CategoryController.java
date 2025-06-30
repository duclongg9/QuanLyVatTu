/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.material;

import dao.material.CategoryMaterialDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CategoryMaterial;
import java.io.IOException;
/**
 *
 * @author Dell-PC
 */@WebServlet(name = "CategoryController", urlPatterns = {"/categoryController"})
public class CategoryController extends HttpServlet {

    CategoryMaterialDAO dao = new CategoryMaterialDAO();

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
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                dao.deleteCategory(delId);
                response.sendRedirect("categoryController");
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
                int endP = total / CategoryMaterialDAO.PAGE_SIZE;
                if (total % CategoryMaterialDAO.PAGE_SIZE != 0) {
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
        int result;
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
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
}
