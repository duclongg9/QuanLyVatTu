package controller;

import dao.MaterialsDAO;
import dao.MaterialItemDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Materials;

@WebServlet(name = "MaterialController", urlPatterns = {"/material"})
public class MaterialController extends HttpServlet {

    private MaterialsDAO materialDAO = new MaterialsDAO();
    private MaterialItemDAO materialItemDAO = new MaterialItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                request.getRequestDispatcher("materialForm.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Materials m = materialDAO.getMaterialById(id);
                request.setAttribute("material", m);
                request.getRequestDispatcher("materialForm.jsp").forward(request, response);
                break;
            case "delete":
                materialDAO.deleteMaterial(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("material");
                break;
            case "confirmDelete":
                int idToConfirm = Integer.parseInt(request.getParameter("id"));
                Materials mcf = materialDAO.getMaterialById(idToConfirm);
                request.setAttribute("material", mcf);
                request.getRequestDispatcher("materialConfirmDelete.jsp").forward(request, response);
                break;
            default:
                List<Materials> list = materialDAO.getAllMaterial();
                request.setAttribute("list", list);
                request.getRequestDispatcher("materialList.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        int unitId = Integer.parseInt(request.getParameter("unitId"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String image = request.getParameter("image");

        if (idStr == null || idStr.isEmpty()) {
            int newId = materialDAO.addMaterial(name, unitId, image, categoryId);
            if (newId != -1) {
                materialItemDAO.createItem(newId, 1); // 1: trạng thái hoạt động
            }
        } else {
            int id = Integer.parseInt(idStr);
            materialDAO.updateMaterial(id, name, unitId, image, categoryId);
        }
        response.sendRedirect("material");
    }

    @Override
    public String getServletInfo() {
        return "Material management";
    }
}