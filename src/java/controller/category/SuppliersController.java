package controller.category;

import com.google.gson.Gson;
import dao.material.MaterialSupplierDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Supplier;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SupplierController", urlPatterns = {"/SuppliersController"})
public class SuppliersController extends HttpServlet {

    MaterialSupplierDAO msdao = new MaterialSupplierDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String materialIdStr = request.getParameter("materialId");
        if (materialIdStr != null) {
            int materialId = Integer.parseInt(materialIdStr);
            List<Supplier> list = msdao.getListSupplierByMaterialId(materialId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String json = new Gson().toJson(list);
            response.getWriter().write(json);
        }
    }
}
