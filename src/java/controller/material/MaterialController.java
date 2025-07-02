
package controller.material;


import dao.subcategory.SubCategoryDAO;
import dao.material.MaterialUnitDAO;
import dao.material.MaterialsDAO;
import dao.material.CategoryMaterialDAO;
import dao.material.MaterialHistoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import model.Materials;
import java.util.Map;
import java.util.HashMap;
import java.sql.SQLException;
/**
 *
 * @author Dell-PC
 */
@MultipartConfig
@WebServlet(name = "MaterialController", urlPatterns = {"/materialController"})
public class MaterialController extends HttpServlet {
public static final int PAGE_NUMBER = 7;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    MaterialUnitDAO muDao = new MaterialUnitDAO();
    SubCategoryDAO scDao = new SubCategoryDAO();
    MaterialsDAO mDao = new MaterialsDAO();
    CategoryMaterialDAO cDao = new CategoryMaterialDAO();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MaterialController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MaterialController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                request.setAttribute("units", muDao.getAllUnit());
                request.setAttribute("categories", scDao.getAllSubCategory());
                if (request.getParameter("success") != null) {
                    request.setAttribute("success", "Thêm vật tư thành công");
                }
                request.getRequestDispatcher("/jsp/material/createMaterial.jsp").forward(request, response);
                break;
            case "history":
                MaterialHistoryDAO hDao = new MaterialHistoryDAO();
                request.setAttribute("historyList", hDao.getAllHistory());
                request.getRequestDispatcher("/jsp/material/materialHistory.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("units", muDao.getAllUnit());
                request.setAttribute("categories", scDao.getAllSubCategory());
                request.setAttribute("material", mDao.getMaterialsById(id));
                request.getRequestDispatcher("/jsp/material/updateMaterial.jsp").forward(request, response);
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                if (mDao.hasRemainingQuantity(deleteId)) {
                    request.setAttribute("error", "Không thể xóa vật tư do còn số lượng tồn");
//                    doGet(request, response);
                } else {
                    mDao.deactivateMaterial(deleteId);
                    response.sendRedirect("materialController?action=list");
                }
                break;
            case "confirmDelete":
                int idToConfirm = Integer.parseInt(request.getParameter("id"));
                Materials mcf = mDao.getMaterialsById(idToConfirm);
                request.setAttribute("material", mcf);
                request.getRequestDispatcher("/jsp/material/materialConfirmDelete.jsp").forward(request, response);
                break;
            case "deleted":
                 String idxPage = request.getParameter("index");
                if (idxPage == null) {
                    idxPage = "1";
                }
                int idx = Integer.parseInt(idxPage);
                try {
                    List<Materials> deleted = mDao.pagingDeletedMaterials(idx);
                    int totalDel = mDao.getTotalDeletedMaterials();
                    int endDel = totalDel / PAGE_NUMBER;
                    if (totalDel % PAGE_NUMBER != 0) {
                        endDel++;
                    }
                    request.setAttribute("list", deleted);
                    request.setAttribute("endP", endDel);
                    request.setAttribute("tag", idx);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
                request.getRequestDispatcher("/jsp/material/DeletedMaterials.jsp").forward(request, response);
                break;
            case "activate":
               int idRestore = Integer.parseInt(request.getParameter("id"));
               mDao.activateMaterial(idRestore);
               response.sendRedirect("materialController?action=deleted");
               break;
           default:
                String indexPage = request.getParameter("index");
                if (indexPage == null) {
                    indexPage = "1";
                }
                int index = Integer.parseInt(indexPage);
                String search = request.getParameter("search");
                String categoryIdParam = request.getParameter("categoryId");
                List<Materials> list;
                int count;
                try {
                    if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
                        int cId = Integer.parseInt(categoryIdParam);
                        list = mDao.searchMaterialsByCategory(cId, index);
                        count = mDao.getTotalMaterialsByCategory(cId);
                        request.setAttribute("selectedCategory", cId);
                    } else if (search != null && !search.trim().isEmpty()) {
                        list = mDao.searchMaterialsByName(search, index);
                        count = mDao.getTotalMaterialsByName(search);
                        request.setAttribute("searchValue", search);
                    } else {
                        list = mDao.pagingMaterials(index);
                        count = mDao.getTotalMaterials();
                    }
                } catch (Exception e) {
                    throw new ServletException(e);
                }

                int endP = count / PAGE_NUMBER;
                if (count % PAGE_NUMBER != 0) {
                    endP++;
                }
                Map<Integer, Materials> updatedMap = new HashMap<>();
                for (Materials m : list) {
                    Materials newVer = mDao.getNewVersionOf(m.getId());
                    if (newVer != null) {
                        updatedMap.put(m.getId(), newVer);
                    }
                }
                request.setAttribute("materials", list);
                request.setAttribute("categoryFilter", cDao.getAllCategory());
                request.setAttribute("updatedMap", updatedMap);
                request.setAttribute("endP", endP);
                request.setAttribute("tag", index);
                
                request.getRequestDispatcher("/jsp/material/listMaterials.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        int unitId = Integer.parseInt(request.getParameter("unitId"));
        int subCategoryId = Integer.parseInt(request.getParameter("subCategoryId"));
        
        if (!isValidName(name)) {
            request.setAttribute("units", muDao.getAllUnit());
            request.setAttribute("categories", scDao.getAllSubCategory());
            request.setAttribute("error", "Tên vật tư không hợp lệ");
            request.getRequestDispatcher("/jsp/material/createMaterial.jsp").forward(request, response);
            return;
        }


        Part imagePart = request.getPart("image");
        String imageName = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            imageName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            imagePart.write(uploadPath + File.separator + imageName);
        }

        int result;
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            if (imageName == null) {
                Materials old = mDao.getMaterialsById(id);
                if (old != null) {
                    imageName = old.getImage();
                }
            }
            result = mDao.updateMaterialWithHistory(id, name, unitId, imageName, subCategoryId);
        } else {
            result = mDao.createMaterial(name, unitId, imageName, subCategoryId);
        }
        if (result > 0) {
                        response.sendRedirect("materialController?action=list");
        } else {
            request.setAttribute("error", "Xử lý thất bại");
            doGet(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private boolean isValidName(String name) {
        if (name == null || name.length() > 50) {
            return false;
        }
        return !name.contains("  ");
    }
}
