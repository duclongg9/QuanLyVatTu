/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.export_warehouse;

import dao.export.OutputDetailDAO;
import dao.export.OutputWarehourseDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OutputDetail;
import model.OutputWarehourse;

@WebServlet(name = "ExportDetailViewController", urlPatterns = {"/exportDetail"})
public class ExportDetailViewController extends HttpServlet {
    private static final int PAGE_NUMBER = 5;

    OutputWarehourseDAO owdao = new OutputWarehourseDAO();
    OutputDetailDAO oddao = new OutputDetailDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exportWarehouseIdParam = request.getParameter("outputWarehouseId");
        if (exportWarehouseIdParam == null || exportWarehouseIdParam.trim().isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }
        int exportWarehouseId = Integer.parseInt(exportWarehouseIdParam);
        request.setAttribute("exportId", exportWarehouseId);
        OutputWarehourse ow = owdao.getOutputWarehourseById(exportWarehouseId);
        request.setAttribute("outputWarehouse", ow);

        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);
        List<OutputDetail> ld = null;
        try {
            ld = oddao.pagingOutputDetailByOutputWarehouseId(index, exportWarehouseId);
        } catch (Exception ex) {
            Logger.getLogger(ExportDetailViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("listOutputDetail", ld);
        request.setAttribute("tag", index);
        int count = oddao.getTotalOutputDetailByOutputWarehouseId(exportWarehouseId);
        int endPage = count / PAGE_NUMBER;
        if (count % PAGE_NUMBER != 0) {
            endPage++;
        }
        request.setAttribute("endP", endPage);
        request.setAttribute("outputWarehouseId", exportWarehouseId);

        request.getRequestDispatcher("/jsp/exportWarehouse/exportDetail.jsp").forward(request, response);
    }
}