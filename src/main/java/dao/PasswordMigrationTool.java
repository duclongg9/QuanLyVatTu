/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.UserDAO;
import Model.User;
import java.util.List;
import units.Encoding;

public class PasswordMigrationTool {
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();
            List<User> users = dao.getAllUser();

            for (User user : users) {
                String oldPassword = user.getPassword();
                String hashedPassword = Encoding.toSHA1(oldPassword);
                System.out.println("Old: " + oldPassword + " => Hashed: " + hashedPassword);

                dao.updatePassword(user.getUsername(), hashedPassword);
                System.out.println("Đã cập nhật mật khẩu cho user: " + user.getUsername());
            }

            System.out.println("✔️ Tất cả mật khẩu đã được mã hóa SHA-1 và cập nhật vào DB.");
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
    }
}
