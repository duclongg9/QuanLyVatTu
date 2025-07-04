/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.qlvt.controller.exportwarehouse;

import com.qlvt.dao.export.OutputWarehourseDAO;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.qlvt.model.OutputWarehourse;

@WebServlet(name = "ListExport", urlPatterns = {"/ListExport"})
public class ListExport extends HttpServlet {

    public static final int PAGE_NUMBER = 5;
    OutputWarehourseDAO dao = new OutputWarehourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String indexPage = request.getParameter("index");
        if (indexPage == null) {
            indexPage = "1";
        }
        int index = Integer.parseInt(indexPage);

        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }


        try {
            List<OutputWarehourse> list;
            int count;
            if (keyword.isEmpty()) {
                list = dao.pagingOutputWarehouse(index);
                count = dao.getTotalOutputWarehouse();
            } else {
                list = dao.searchOutputWarehouse(keyword, index);
                count = dao.countSearchOutputWarehouse(keyword);
            }

            request.setAttribute("outputWarehouses", list);
            request.setAttribute("tag", index);
            int endPage = count / PAGE_NUMBER;
            if (count % PAGE_NUMBER != 0) {
                endPage++;
            }
            request.setAttribute("endP", endPage);
            request.setAttribute("keyword", keyword);

            
            request.getRequestDispatcher("/jsp/exportWarehouse/Export.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ListExport.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
    }
}
