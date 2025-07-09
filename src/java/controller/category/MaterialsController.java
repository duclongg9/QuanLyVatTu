package controller.category;

import com.google.gson.Gson;
import dao.material.MaterialsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Materials;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "MaterialController", urlPatterns = {"/MaterialsController"})
public class MaterialsController extends HttpServlet {

    MaterialsDAO mdao = new MaterialsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subId = request.getParameter("subId");
        if (subId != null) {
            int subCategoryId = Integer.parseInt(subId);
            List<Materials> list = null;
            try {
                list = mdao.getMaterialListBySubCateId(subCategoryId);
            } catch (SQLException ex) {
                Logger.getLogger(MaterialsController.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String json = new Gson().toJson(list);
            response.getWriter().write(json);
        }
    }
}
