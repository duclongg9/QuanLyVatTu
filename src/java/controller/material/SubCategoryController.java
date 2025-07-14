package controller.material;

import dao.Category.CategoryDAO;
import dao.material.MaterialsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.User;

@WebServlet(name = "SubCategoryController", urlPatterns = {"/subCategoryController"})
public class SubCategoryController extends HttpServlet {
    CategoryDAO dao = new CategoryDAO();
    CategoryDAO cDao = new CategoryDAO();
    MaterialsDAO materialsDAO = new MaterialsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
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
                request.setAttribute("sub", dao.getCategoryById(id));
                request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
                break;
            case "deleted":
                String deletedIndex = request.getParameter("index");
                if (deletedIndex == null) deletedIndex = "1";
                int delIdx = Integer.parseInt(deletedIndex);
                request.setAttribute("subCategories", dao.pagingDeletedSubCategory(delIdx));
                int totalD = dao.getTotalDeletedSubCategory();
                int endPD = totalD / CategoryDAO.PAGE_SIZE;
                if (totalD % CategoryDAO.PAGE_SIZE != 0) endPD++;
                request.setAttribute("endP", endPD);
                request.setAttribute("tag", delIdx);
                request.getRequestDispatcher("/jsp/material/deletedSubCategory.jsp").forward(request, response);
                break;
            case "activate":
                int acId = Integer.parseInt(request.getParameter("id"));
                dao.activateSubCategory(acId,loggedInUser.getId());
                response.sendRedirect("subCategoryController?action=deleted");
                break;
            case "delete":
                int delId = Integer.parseInt(request.getParameter("id"));
                if (materialsDAO.getTotalMaterialsBySubCategory(delId) > 0) {
                    request.setAttribute("error", "Không thể xóa do còn vật tư phụ thuộc");
                    doGet(request, response);
                } else {
                    dao.deleteSubCategory(delId,loggedInUser.getId());
                    response.sendRedirect("subCategoryController");
                }
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
                int endP = total / CategoryDAO.PAGE_SIZE;
                if (total % CategoryDAO.PAGE_SIZE != 0) endP++;
                request.setAttribute("categoryFilter", cDao.getAllCategory());
                request.setAttribute("endP", endP);
                request.setAttribute("tag", index);
                request.getRequestDispatcher("/jsp/material/listSubCategory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("account");

        if (loggedInUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        if (!isValidName(name)) {
            request.setAttribute("categories", cDao.getAllCategory());
            request.setAttribute("error", "Tên loại vật tư không hợp lệ");
            request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
            return;
        }

        Integer id = null;
        if (idParam != null && !idParam.isEmpty()) {
            id = Integer.parseInt(idParam);
        }

        if (dao.isNameExists(name, id)) {
            request.setAttribute("categories", cDao.getAllCategory());
            request.setAttribute("error", "Tên loại vật tư đã tồn tại");
            request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
            return;
        }

        int result;
        if (id != null) {
            result = dao.updateSubCategory(id, name, categoryId,loggedInUser.getId());
        } else {
            result = dao.createSubCategory(name, categoryId,loggedInUser.getId());
        }
        if (result > 0) {
            response.sendRedirect("subCategoryController");
        } else {
            request.setAttribute("categories", cDao.getAllCategory());
            request.setAttribute("error", "Xử lý thất bại");
            request.getRequestDispatcher("/jsp/material/createSubCategory.jsp").forward(request, response);
        }
    }
    
    private boolean isValidName(String name) {
        if (name == null || name.length() > 50) return false;
        return !name.contains("  ");
    }
}
