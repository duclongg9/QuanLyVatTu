/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.request;

import dao.request.requestDAO;
import dao.request.requestDetailDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Request;
import model.RequestDetail;
import model.RequestType;

@WebServlet(name = "ApprovalController", urlPatterns = {"/requestApproval"})
public class ApprovalController extends HttpServlet {

    requestDAO rdao = new requestDAO();
    requestDetailDAO rddao = new requestDetailDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Request> all = rdao.getAllRequest();
        Map<Integer, List<RequestDetail>> detailMap = new HashMap<>();
        for (Request r : all) {
            if (r.getType() == RequestType.EXPORT && r.getStatusId().getId() == 1) {
                detailMap.put(r.getId(), rddao.getAllMaterialsInRequest(r.getId()));
            }
        }
        request.setAttribute("requests", all);
        request.setAttribute("detailMap", detailMap);
        request.getRequestDispatcher("/jsp/request/requestApproval.jsp").forward(request, response);
    }
}
