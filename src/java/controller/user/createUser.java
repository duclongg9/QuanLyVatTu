/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;


import dao.role.RoleDAO;
import dao.user.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import model.Role;
import model.User;
import units.RandomCode;
import units.SendMail;
import units.Validator;
import units.Encoding;

/**
 *
 * @author D E L L
 */
@MultipartConfig
@WebServlet(name = "creatUser", urlPatterns = {"/createUser"})
public class createUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // In toàn bộ thông tin gửi lên
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            System.out.println("Part Name: " + part.getName());
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    UserDAO udao = new UserDAO();
    User u = new User();
    RoleDAO rdao = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Lấy list role
        List<Role> lr = rdao.getAllRole();
        request.setAttribute("listRole", lr);

        request.getRequestDispatcher("/jsp/user/createUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = getValue(request.getPart("username"));
            String fullname = getValue(request.getPart("fullname"));
            String phone = getValue(request.getPart("phone"));
            String password = RandomCode.generateRandomString(8);
            String email = getValue(request.getPart("email"));
            String address = getValue(request.getPart("address"));
            String birthDate = getValue(request.getPart("birthDate"));
            String genderParam = getValue(request.getPart("gender"));
            String statusParam = getValue(request.getPart("status"));
            String roleIdParam = getValue(request.getPart("roleId"));
            String encodingPassword = Encoding.toSHA1(password);
            boolean gender = Boolean.parseBoolean(genderParam);
            boolean status = Boolean.parseBoolean(statusParam);
            int roleId = Integer.parseInt(roleIdParam);

            //lấy lại list vai trò 
            List<Role> lr = rdao.getAllRole();

            String errorMessage = "";

            boolean emailExists = udao.isEmailExists(email);
            boolean phoneExists = udao.isPhoneExits(phone);

            if (request.getParameter("roleId") == null || request.getParameter("roleId").isEmpty()) {
                errorMessage = "Vui lòng chọn role!";
            }

            if (emailExists && phoneExists) {
                errorMessage = "Email và số điện thoại đã tồn tại!";
            } else if (emailExists) {
                errorMessage = "Email đã tồn tại";
            } else if (phoneExists) {
                errorMessage = "Số điện thoại đã tồn tại";
            }
            if (!Validator.isValidPhone(phone)) {
                errorMessage += " Số điện thoại không hợp lệ (phải bắt đầu bằng 0 và đủ 10 chữ số)";
            }
            
            if (!errorMessage.isEmpty()) {
                request.setAttribute("error", errorMessage);
                request.setAttribute("listRole", lr);
                request.getRequestDispatcher("/jsp/user/createUser.jsp").forward(request, response);
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

            int updated = udao.createUser(username, fullname, phone, encodingPassword, email, address, gender, birthDate, imageName, status, roleId);
            System.out.println(updated);
            if (updated > 0) {// Gửi email ở đây
                String subject = "Thông tin tài khoản của bạn";
                String messageText = "Chào " + fullname + ",\n\n"
                        + "Tài khoản của bạn đã được tạo thành công.\n"
                        + "Tên đăng nhập: " + username + "\n"
                        + "Mật khẩu: " + password + "\n\n"
                        + "Vui lòng đổi mật khẩu sau khi đăng nhập lần đầu.\n\n"
                        + "Trân trọng,\nAdmin Material Management";

                try {
                    SendMail.sendMail(email, subject, messageText);
                } catch (Exception e) {
                    e.printStackTrace(); // log lỗi gửi mail (không làm hỏng luồng chính)
                }
                response.sendRedirect("userList");
            } else {
                request.setAttribute("error", "Tạo user thất bại" + updated);
                request.setAttribute("listRole", lr);
                request.getRequestDispatcher("/jsp/user/createUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            request.getRequestDispatcher("/jsp/user/createUser.jsp").forward(request, response);
        }
    }

    private String getValue(Part part) throws IOException {
        InputStream inputStream = part.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes, "UTF-8").trim();
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
