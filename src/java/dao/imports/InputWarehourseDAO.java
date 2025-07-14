/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.auditLog.AuditLogDAO;
import dao.connect.DBConnect;
import dao.user.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ActionType;
import model.InputWarehourse;
import model.Request;

import model.User;

/**
 *
 * @author D E L L
 */
public class InputWarehourseDAO {

    private static final String COL_ID = "id";
    private static final String COL_DATEINPUT = "dateInput";
    private static final String COL_USERID = "userId";

    private Connection conn;

    UserDAO udao = new UserDAO();
    requestDAO rdao = new requestDAO();

    public double getTotalValueByInputWarehouseId(int inputWarehouseId) {
        double totalValue = 0;
        String sql = "SELECT SUM(quantity * inputPrice) AS total_input_value "
                + "FROM inputdetail WHERE inputWarehouseId = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inputWarehouseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalValue = rs.getDouble("total_input_value");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalValue;
    }

    public InputWarehourse getInputWarehourseById(int inputWarehourseId) {
        String sql = "SELECT * FROM inputwarehouse WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) { //Sử dụng try-with-Resource để đóng tài nguyên sau khi sử dụng

            ps.setInt(1, inputWarehourseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InputWarehourse iw = new InputWarehourse();
                    iw.setId(rs.getInt(COL_ID));
                    iw.setDateInput(rs.getString(COL_DATEINPUT));
                    iw.setUserId(udao.getUserById(rs.getInt(COL_USERID)));
                    return iw;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<InputWarehourse> getFilteredInputWarehouses(String fromDate, String toDate, int index, int pageSize) throws SQLException {
        List<InputWarehourse> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM InputWarehouse WHERE 1=1 ");

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND dateInput >= ? ");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND dateInput <= ? ");
        }

        sql.append("ORDER BY dateInput DESC LIMIT ?, ?");

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(paramIndex++, fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(paramIndex++, toDate);
            }

            ps.setInt(paramIndex++, (index - 1) * pageSize);
            ps.setInt(paramIndex, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InputWarehourse iw = new InputWarehourse();
                iw.setId(rs.getInt(COL_ID));
                iw.setDateInput(rs.getString(COL_DATEINPUT));
                iw.setUserId(udao.getUserById(rs.getInt(COL_USERID))); // map như cũ

                list.add(iw);
            }
        }
        return list;
    }

    public int countFilteredInputWarehouses(String fromDate, String toDate) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM InputWarehouse WHERE 1=1 ");

        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND dateInput >= ? ");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND dateInput <= ? ");
        }

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (fromDate != null && !fromDate.isEmpty()) {
                ps.setString(paramIndex++, fromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                ps.setString(paramIndex++, toDate);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Insert phiếu nhập kho mới, trả về inputWarehouseId vừa tạo
    public int insertInputWarehouse(int userId) throws SQLException {
        String sql = "INSERT INTO InputWarehouse (dateInput, userId) VALUES (NOW(), ?)";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            int importId = -1;

            ps.executeUpdate(); // Thực hiện insert

            // Lấy ID mới sinh
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    importId = rs.getInt(1);
                }
            }

            User user = udao.getUserById(userId);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã thực hiện nhập kho ";
            logDAO.insertAuditLog("InputWarehouse", importId, ActionType.INSERT, message, userId);

            return importId;

        }

    }

    public static void main(String[] args) {
        InputWarehourseDAO i = new InputWarehourseDAO();
        InputWarehourse imports = i.getInputWarehourseById(1);
        System.out.println(imports);
    }
}
