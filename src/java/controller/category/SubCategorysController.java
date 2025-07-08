package controller.category;

import com.google.gson.Gson;
import dao.Category.CategoryDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Category;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SubCategoryController", urlPatterns = {"/SubCategorysController"})
public class SubCategorysController extends HttpServlet {

    CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parentId = request.getParameter("parentId");
        if (parentId != null) {
            int parentCatId = Integer.parseInt(parentId);
            List<Category> list = dao.getSubCategoryByCatId(parentCatId);  // Lấy danh sách con

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String json = new Gson().toJson(list);
            response.getWriter().write(json);

           
        }

    }
}
