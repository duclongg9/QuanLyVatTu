
package controller;

import dao.Roledao;
import dao.Userdao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Role;
import model.User;
import units.Validator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

@MultipartConfig
@WebServlet(name = "CreateUser", urlPatterns = {"/CreateUser"})
public class CreateUserServlet extends HttpServlet {

    private final Userdao udao = new Userdao();
    private final Roledao rdao = new Roledao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Role> roles = rdao.getAllRole();
        request.setAttribute("listRole", roles);
        request.getRequestDispatcher("Admin/createUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = getValue(request.getPart("username"));
            String fullname = getValue(request.getPart("fullname"));
            String phone = getValue(request.getPart("phone"));
            String password = getValue(request.getPart("password"));
            String email = getValue(request.getPart("email"));
            String address = getValue(request.getPart("address"));
            boolean gender = Boolean.parseBoolean(getValue(request.getPart("gender")));
            String birthDate = getValue(request.getPart("birthDate"));
            boolean status = Boolean.parseBoolean(getValue(request.getPart("status")));
            int roleId = Integer.parseInt(getValue(request.getPart("roleId")));

            List<Role> roles = rdao.getAllRole();
            String errorMessage = "";

            if (udao.isEmailExists(email)) {
                errorMessage += "Email đã tồn tại. ";
            }
            if (udao.isPhoneExits(phone)) {
                errorMessage += "Số điện thoại đã tồn tại. ";
            }
            if (!Validator.isValidPhone(phone)) {
                errorMessage += "Số điện thoại không hợp lệ. ";
            }

            if (!errorMessage.isEmpty()) {
                request.setAttribute("error", errorMessage);
                request.setAttribute("listRole", roles);
                request.getRequestDispatcher("/Admin/createUser.jsp").forward(request, response);
                return;
            }

            Part imagePart = request.getPart("image");
            String imageName = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                imageName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/images");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                imagePart.write(uploadPath + File.separator + imageName);
            }

            boolean success = udao.createUser(username, fullname, phone, password, email, address, gender, birthDate, imageName, status, roleId);
            if (success) {
                response.sendRedirect("userList");
            } else {
                request.setAttribute("error", "Tạo user thất bại");
                request.getRequestDispatcher("Admin/createUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            request.getRequestDispatcher("Admin/createUser.jsp").forward(request, response);
        }
    }

    private String getValue(Part part) throws IOException {
        try (Scanner scanner = new Scanner(part.getInputStream(), "UTF-8")) {
            scanner.useDelimiter("");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
