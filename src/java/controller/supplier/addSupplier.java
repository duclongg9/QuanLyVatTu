/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.supplier;




import units.Validator;
import dao.Supplier.SupplierDAO;
import model.Supplier;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "addSupplier", urlPatterns = {"/addsupplier"})
public class addSupplier extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet addSupplier</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addSupplier at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        try {

            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            // Chuẩn hóa dữ liệu đầu vào
            name = (name != null) ? name.trim().replaceAll("\\s+", " ") : "";
            phone = (phone != null) ? phone.trim() : "";
            address = (address != null) ? address.trim().replaceAll("\\s+", " ") : "";
            
            String sub = request.getParameter("sub");
            if(sub==null){
                request.getRequestDispatcher("jsp/supplier/addSupplier.jsp").forward(request, response);
                return;
            }
            

            // Kiểm tra rỗng
            if (Validator.isNullOrEmpty(name)
                    || Validator.isNullOrEmpty(phone)
                    || Validator.isNullOrEmpty(address)) {

                request.setAttribute("msg", "Vui lòng điền đầy đủ thông tin!");
                request.getRequestDispatcher("jsp/supplier/addSupplier.jsp").forward(request, response);
                return;
            }

            // Kiểm tra định dạng tên
            if (!Validator.isValidNameSup(name)) {
                request.setAttribute("msg", "Tên không hợp lệ! Không chứa số hoặc ký tự đặc biệt.");
                request.getRequestDispatcher("jsp/supplier/addSupplier.jsp").forward(request, response);
                return;
            }

            // Kiểm tra định dạng số điện thoại
            if (!Validator.isValidPhone(phone)) {
                request.setAttribute("msg", "Số điện thoại không hợp lệ! Phải gồm 10 chữ số và bắt đầu bằng số 0.");
                request.getRequestDispatcher("jsp/supplier/addSupplier.jsp").forward(request, response);
                return;
            }

            // Kiểm tra định dạng địa chỉ
            if (!Validator.isValidAddress(address)) {
                request.setAttribute("msg", "Địa chỉ không hợp lệ! Không được chứa ký tự đặc biệt.");
                request.getRequestDispatcher("jsp/supplier/addSupplier.jsp").forward(request, response);
                return;
            }

            // Gọi DAO
            SupplierDAO dao = new SupplierDAO();

            // Kiểm tra trùng số điện thoại
            if (dao.isPhoneExists(phone)) {
                request.setAttribute("msg", "Số điện thoại này đã tồn tại!");
                request.getRequestDispatcher("jsp/supplier/addSupplier.jsp").forward(request, response);
                return;
            }

            boolean success = dao.addSupplier(name, phone, address);
            
            
             

            if (success) {
                response.sendRedirect("suppliercontroller");
            } else {
                request.setAttribute("msg", "Thêm nhà cung cấp thất bại. Vui lòng thử lại.");
                request.getRequestDispatcher("jsp/supplier/addSupplier.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

