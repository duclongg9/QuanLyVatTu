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
    SubCategoryDAO dao = new SubCategoryDAO();
    CategoryMaterialDAO cDao = new CategoryMaterialDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "add":
                request.setAttribute("categories", cDao.getAllCategory());
                request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("categories", cDao.getAllCategory());
                request.setAttribute("sub", dao.getSubCategoryById(id));
                request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                dao.deleteSubCategory(delId);
                response.sendRedirect("subCategoryController");
                break;
            default:
                String indexPage = request.getParameter("index");
                if (indexPage == null) indexPage = "1";
                int index = Integer.parseInt(indexPage);
                String search = request.getParameter("search");
                String catId = request.getParameter("categoryId");
                int total;
                if (catId != null && !catId.isEmpty()) {
                    int cid = Integer.parseInt(catId);
                    request.setAttribute("subCategories", dao.searchSubCategoryByCategory(cid, index));
                    total = dao.getTotalSubCategoryByCategory(cid);
                    request.setAttribute("selectedCategory", cid);
                } else if (search != null && !search.trim().isEmpty()) {
                    request.setAttribute("subCategories", dao.searchSubCategoryByName(search, index));
                    total = dao.getTotalSubCategoryByName(search);
                    request.setAttribute("searchValue", search);
                } else {
                    request.setAttribute("subCategories", dao.pagingSubCategory(index));
                    total = dao.getTotalSubCategory();
                }
                int endP = total / SubCategoryDAO.PAGE_SIZE;
                if (total % SubCategoryDAO.PAGE_SIZE != 0) endP++;
                request.setAttribute("categoryFilter", cDao.getAllCategory());
                request.setAttribute("endP", endP);
                request.setAttribute("tag", index);
                request.getRequestDispatcher("/jsp/material/listSubCategory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        int result;
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            result = dao.updateSubCategory(id, name, categoryId);
        } else {
            result = dao.createSubCategory(name, categoryId);
        }
        if (result > 0) {
            response.sendRedirect("subCategoryController");
        } else {
            request.setAttribute("categories", cDao.getAllCategory());
            request.setAttribute("error", "Xử lý thất bại");
            request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
        }
    }
}
