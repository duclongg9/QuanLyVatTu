/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.material;

import dao.unit.UnitDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Unit;

/**
 *
 * @author KIET
 */
@WebServlet(name = "UnitServlet", urlPatterns = {"/unit"})
public class UnitServlet extends HttpServlet {

    private UnitDAO unitDAO = new UnitDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UnitServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UnitServlet at " + request.getContextPath() + "</h1>");
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

    HttpSession session = request.getSession();
    String action = request.getParameter("action");
    if (action == null) {
        action = "list";
    }

    String message = (String) session.getAttribute("message");
    String messageType = (String) session.getAttribute("messageType");
    session.removeAttribute("message");
    session.removeAttribute("messageType");

    switch (action) {
        case "add":
            if (message != null) {
                request.setAttribute("message", message);
                request.setAttribute("messageType", messageType);
            }
            request.getRequestDispatcher("jsp/unit/unitForm.jsp").forward(request, response);
            break;

        case "edit":
            int id = Integer.parseInt(request.getParameter("id"));
            Unit u = unitDAO.getUnitById(id);
            request.setAttribute("unit", u);
            if (message != null) {
                request.setAttribute("message", message);
                request.setAttribute("messageType", messageType);
            }
            request.getRequestDispatcher("jsp/unit/unitForm.jsp").forward(request, response);
            break;

        case "delete":
            int deleteId = Integer.parseInt(request.getParameter("id"));
            if (unitDAO.isUsedInMaterials(deleteId)) {
                session.setAttribute("message", "Không thể xóa đơn vị đang được sử dụng.");
                session.setAttribute("messageType", "danger");
            } else {
                unitDAO.deleteUnit(deleteId);
                session.setAttribute("message", "Xóa đơn vị thành công.");
                session.setAttribute("messageType", "success");
            }
            response.sendRedirect("unit");
            break;

        case "confirmDelete":
            int idToConfirm = Integer.parseInt(request.getParameter("id"));
            Unit unitToConfirm = unitDAO.getUnitById(idToConfirm);
            request.setAttribute("unit", unitToConfirm);
            request.getRequestDispatcher("jsp/unit/unitConfirmDelete.jsp").forward(request, response);
            break;

        case "search":
            String keyword = request.getParameter("keyword");
            List<Unit> searchResults = unitDAO.searchByUnit(keyword);
            request.setAttribute("list", searchResults);
            request.setAttribute("keyword", keyword);
            if (message != null) {
                request.setAttribute("message", message);
                request.setAttribute("messageType", messageType);
            }
            request.getRequestDispatcher("jsp/unit/unitList.jsp").forward(request, response);
            break;

        default: 
            int page = 1, size = 5;
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                try {
                    page = Integer.parseInt(pageStr);
                } catch (NumberFormatException ignored) {}
            }
            
            List<Unit> list = unitDAO.getUnitsByPage(page, size);
            int total = unitDAO.countUnits();
            int totalPage = (int) Math.ceil((double) total / size);

            request.setAttribute("list", list);
            request.setAttribute("page", page);
            request.setAttribute("totalPage", totalPage);
            if (message != null) {
                request.setAttribute("message", message);
                request.setAttribute("messageType", messageType);
            }

            request.getRequestDispatcher("jsp/unit/unitList.jsp").forward(request, response);
            break;
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
//        HttpSession session = request.getSession();
//        String idStr = request.getParameter("id");
//        String name = request.getParameter("name");
//
//        if (unitDAO.isDuplicateName(name)) {
//            session.setAttribute("message", "Tên đơn vị đã tồn tại.");
//            session.setAttribute("messageType", "warning");
//            response.sendRedirect("unit?action=add");
//            return;
//        }
//
//        if (idStr == null || idStr.isEmpty()) {
//            unitDAO.addOrRestoreUnit(name);
//            session.setAttribute("message", "Thêm đơn vị thành công.");
//            session.setAttribute("messageType", "success");
//        } else {
//            int id = Integer.parseInt(idStr);
//            unitDAO.updateUnit(id, name);
//            session.setAttribute("message", "Cập nhật đơn vị thành công.");
//            session.setAttribute("messageType", "success");
//        }
//        List<Unit> list = unitDAO.getAllUnits();
//request.setAttribute("list", list);
//request.getRequestDispatcher("unitList.jsp").forward(request, response);
//
//        response.sendRedirect("unit");
//    
        HttpSession session = request.getSession();
    String idStr = request.getParameter("id");
    String unitName = request.getParameter("unit");

    try {
        if (idStr == null || idStr.isEmpty()) {
            unitDAO.addUnit(unitName); 
            session.setAttribute("message", "Thêm đơn vị thành công!");
        } else {
            int id = Integer.parseInt(idStr);
            unitDAO.updateUnit(id, unitName); 
            session.setAttribute("message", "Cập nhật đơn vị thành công!");
        }
        session.setAttribute("messageType", "success");
        response.sendRedirect(request.getContextPath() + "/unit");
    } catch (RuntimeException e) {
        session.setAttribute("message", e.getMessage());
        session.setAttribute("messageType", "danger");
        response.sendRedirect(request.getContextPath() + "/unit?action=add");
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
    }// </editor-fold>

}
