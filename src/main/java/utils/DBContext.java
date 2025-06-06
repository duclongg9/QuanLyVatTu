package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    private static final String URL = "jdbc:mysql://localhost:3306/ql_vattu"; // <--- sửa DB name
    private static final String USERNAME = "root"; // <--- sửa nếu user khác
    private static final String PASSWORD = "Quany@1234";     // <--- nhập mật khẩu nếu có

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // dùng cho MySQL 8.x
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
