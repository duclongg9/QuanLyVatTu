/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.export_warehourse;

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
import model.OutputWarehourse;

@WebServlet(name = "ListExport", urlPatterns = {"/ListExport"})
public class ListExport extends HttpServlet {

    OutputWarehourseDAO dao = new OutputWarehourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<OutputWarehourse> list = dao.getAllOutputWarehouses();
            request.setAttribute("outputWarehouses", list);
            request.getRequestDispatcher("/jsp/exportWarehouse/Export.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ListExport.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
    }
}
