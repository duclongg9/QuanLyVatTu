/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.qlvt.controller.supplier;

import com.qlvt.dao.supplier.SupplierDAO;
import com.qlvt.units.Validator;
import com.qlvt.model.Supplier;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "processUpdateSupplier", urlPatterns = {"/processupdatesupplier"})
public class processUpdateSupplier extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            boolean status = Boolean.parseBoolean(request.getParameter("status"));

            // Chuẩn hóa dữ liệu đầu vào
            name = (name != null) ? name.trim().replaceAll("\\s+", " ") : "";
            phone = (phone != null) ? phone.trim() : "";
            address = (address != null) ? address.trim().replaceAll("\\s+", " ") : "";

            Supplier supplier = new Supplier(id, name, phone, address, status);

            // Kiểm tra dữ liệu
            if (Validator.isNullOrEmpty(name)
                    || Validator.isNullOrEmpty(phone)
                    || Validator.isNullOrEmpty(address)) {
                request.setAttribute("msg", "Vui lòng nhập đầy đủ thông tin!");
                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("jsp/supplier/updateSupplier.jsp").forward(request, response);
                return;
            }

            if (!Validator.isValidNameSup(name)) {
                request.setAttribute("msg", "Tên không hợp lệ! Không được chứa số hoặc ký tự đặc biệt.");
                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("jsp/supplier/updateSupplier.jsp").forward(request, response);
                return;
            }

            if (!Validator.isValidPhone(phone)) {
                request.setAttribute("msg", "Số điện thoại không hợp lệ! Phải có 10 chữ số và bắt đầu bằng 0.");
                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("jsp/supplier/updateSupplier.jsp").forward(request, response);
                return;
            }

            if (!Validator.isValidAddress(address)) {
                request.setAttribute("msg", "Địa chỉ không hợp lệ! Không được chứa ký tự đặc biệt.");
                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("jsp/supplier/updateSupplier.jsp").forward(request, response);
                return;
            }

            // Nếu dữ liệu hợp lệ
            SupplierDAO dao = new SupplierDAO();
            // Kiểm tra số điện thoại đã tồn tại ở nhà cung cấp khác
            if (dao.isPhoneExistsForOtherSupplier(phone, id)) {
                request.setAttribute("msg", "Số điện thoại này đã được sử dụng bởi nhà cung cấp khác!");
                request.setAttribute("supplier", supplier);
                request.getRequestDispatcher("jsp/supplier/updateSupplier.jsp").forward(request, response);
                return;
            }

            dao.updateSupplier(supplier);

            // Chuyển hướng về danh sách supplier
            response.sendRedirect("suppliercontroller");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }

    }

    @Override
    public String getServletInfo() {
        return "Xử lý cập nhật nhà cung cấp";
    }
}