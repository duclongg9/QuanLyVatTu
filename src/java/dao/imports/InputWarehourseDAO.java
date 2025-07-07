/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

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
import model.InputWarehourse;
import model.Request;
import model.RequestType;
import model.User;

/**
 *
 * @author D E L L
 */
public class InputWarehourseDAO {

    private static final String COL_ID = "id";
    private static final String COL_DATEINPUT = "dateInput";
    private static final String COL_USERID = "userId";
    private static final String COL_REASON = "reason";
    private static final String COL_NOTE = "note";
    private static final String COL_REQUEST = "requestId";

    private Connection conn;

    UserDAO udao = new UserDAO();
    requestDAO rdao = new requestDAO();
    
    public double getTotalValueByInputWarehouseId(int inputWarehouseId) {
    double totalValue = 0;
    String sql = "SELECT SUM(quantity * inputPrice) AS total_input_value " +
                 "FROM inputdetail WHERE inputWarehouseId = ?";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

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
                    iw.setReason(rs.getString(COL_REASON));
                    iw.setNote(rs.getString(COL_NOTE));
                    iw.setRequest(rdao.getRequestById(rs.getInt(COL_REQUEST)));
                    return iw;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<InputWarehourse> getAllInputWarehouses() throws SQLException {
        List<InputWarehourse> list = new ArrayList<>();
        String sql = "SELECT * FROM InputWarehouse";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InputWarehourse iw = new InputWarehourse();
                iw.setId(rs.getInt(COL_ID));
                iw.setDateInput(rs.getString(COL_DATEINPUT));
                iw.setUserId(udao.getUserById(rs.getInt(COL_USERID)));
                iw.setReason(rs.getString(COL_REASON));
                iw.setNote(rs.getString(COL_NOTE));
                iw.setRequest(rdao.getRequestById(rs.getInt(COL_REQUEST)));

                list.add(iw);
            }
        }
        return list;
    }

    // Insert phiếu nhập kho mới, trả về inputWarehouseId vừa tạo
    public int insertInputWarehouse(int userId) throws SQLException {
        String sql = "INSERT INTO InputWarehouse (dateInput, userId) VALUES (NOW(), ?)";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        InputWarehourseDAO i = new InputWarehourseDAO();
        InputWarehourse imports = i.getInputWarehourseById(1);
        System.out.println(imports);
    }
}
