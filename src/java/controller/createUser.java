/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.RoleDAO;
import DAO.UserDAO;
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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Role;
import model.User;
import units.Validator;

/**
 *
 * @author D E L L
 */
@MultipartConfig
@WebServlet(name = "creatUser", urlPatterns = {"/Create"})
public class createUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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

            //lấy lại list vai trò 
            List<Role> lr = rdao.getAllRole();

            String errorMessage = "";

            boolean emailExists = udao.isEmailExists(email);
            boolean phoneExists = udao.isPhoneExits(phone);

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

            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
                request.setAttribute("listRole", lr);
                request.getRequestDispatcher("Admin/createUser.jsp").forward(request, response);
                return;
            }

            Part imagePart = request.getPart("image");
            String imageName;
            if (imagePart != null && imagePart.getSize() > 0) {
                imageName = Path.of(imagePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/images");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                imagePart.write(uploadPath + File.separator + imageName);
            } else {
                imageName = null;
            }

            User user = new User();
            user.setUsername(username);
            user.setFullName(fullname);
            user.setPhone(phone);
            user.setPassword(password);
            user.setEmail(email);
            user.setAddress(address);
            user.setGender(gender);
            user.setBirthDate(birthDate);
            user.setImage(imageName);
            user.setStatus(status);
            user.setRole(rdao.getRoleById(roleId));

            boolean updated = udao.createUser(username, fullname, phone, password, email, address, gender, birthDate, imageName, status, roleId);

            if (updated) {
                response.sendRedirect("userList");
            } else {
                request.setAttribute("error", "Tạo user thất bại");
                request.getRequestDispatcher("Admin/updateUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý: " + e.getMessage());
            request.getRequestDispatcher("Admin/createUser.jsp").forward(request, response);
        }
    }

    private String getValue(Part part) throws IOException {
        try (Scanner scanner = new Scanner(part.getInputStream(), "UTF-8")) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
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
