
package controller;

import dao.Userdao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserController", urlPatterns = {"/userList"})
public class UserControllerServlet extends HttpServlet {

    private final Userdao userDao = new Userdao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> list = userDao.getAllUser();
        request.setAttribute("listUser", list);
        request.getRequestDispatcher("Admin/listUser.jsp").forward(request, response);
    }
}
