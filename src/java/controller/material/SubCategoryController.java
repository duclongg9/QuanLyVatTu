/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.material;

import dao.material.CategoryMaterialDAO;
import dao.subcategory.SubCategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SubCategoryController", urlPatterns = {"/subCategoryController"})
public class SubCategoryController extends HttpServlet {
    SubCategoryDAO scDao = new SubCategoryDAO();
    CategoryMaterialDAO cmDao = new CategoryMaterialDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                request.setAttribute("categories", cmDao.getAllCategory());
                request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("categories", cmDao.getAllCategory());
                request.setAttribute("subCategory", scDao.getSubCategoryById(id));
                request.getRequestDispatcher("/jsp/material/updateSubCategory.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("subcategories", scDao.getAllSubCategory());
                request.getRequestDispatcher("/jsp/material/listSubCategory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("subCategoryName");
        int categoryId = Integer.parseInt(request.getParameter("categoryMaterialId"));
        int result;
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            result = scDao.updateSubCategory(id, name, categoryId);
        } else {
            result = scDao.createSubCategory(name, categoryId);
        }
        if (result > 0) {
            response.sendRedirect("subCategoryController");
        } else {
            request.setAttribute("error", "Xử lý thất bại");
            doGet(request, response);
        }
    }
}