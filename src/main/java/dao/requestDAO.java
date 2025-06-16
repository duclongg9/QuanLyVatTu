/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Request;
import model.RequestType;

/**
 *
 * @author D E L L
 */
public class requestDAO {

    private Connection conn;
    UserDAO udao = new UserDAO();
    requestStatusDAO rsdao = new requestStatusDAO();

    private static final int PAGE_SIZE = 5;

    private static final String COL_ID = "id";
    private static final String COL_DATE = "date";
    private static final String COL_STATUS = "statusId";
    private static final String COL_USER = "userId";
    private static final String COL_NOTE = "note";
    private static final String COL_TYPE = "type";
    private static final String COL_APPROVEDBY = "approvedBy";

    public requestDAO(Connection conn) {
        this.conn = conn;
    }

    public requestDAO() {
        this(DBConnect.getConnection());
    }

    public List<Request> getAllRequest() {
        List<Request> list = new ArrayList<>();
        String sql = " SELECT * FROM request ORDER BY id DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                list.clear();
                while (rs.next()) {
                    Request r = new Request();
                    r.setId(rs.getInt(COL_ID));
                    r.setDate(rs.getString(COL_DATE));
                    r.setStatusId(rsdao.getStatusById(rs.getInt(COL_STATUS)));
                    r.setUserId(udao.getUserById(rs.getInt(COL_USER)));
                    r.setNote(rs.getString(COL_NOTE));
                    String typeStr = rs.getString(COL_TYPE); // Lấy giá trị từ DB: "Import" hoặc "Export"
                    RequestType type = RequestType.valueOf(typeStr.toUpperCase()); // Convert String -> Enum
                    r.setType(type); // set đúng kiểu RequestType
                    r.setApprovedBy(udao.getUserById(rs.getInt(COL_APPROVEDBY)));
                    list.add(r);
                }
                return list;
            }

        } catch (SQLException ex) {

            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //đếm số lượng yêu cầu trong database
    public int getTotalRequest() {
        String sql = "SELECT COUNT(*) FROM request;";
        try (PreparedStatement ps = conn.prepareStatement(sql);) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    //Phân trang
    public List<Request> pagingStaff(int index) throws SQLException {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM request\n"
                + "LIMIT ? OFFSET ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    Request r = new Request();
                    r.setId(rs.getInt(COL_ID));
                    r.setDate(rs.getString(COL_DATE));
                    r.setStatusId(rsdao.getStatusById(rs.getInt(COL_STATUS)));
                    r.setUserId(udao.getUserById(rs.getInt(COL_USER)));
                    r.setNote(rs.getString(COL_NOTE));
                    String typeStr = rs.getString(COL_TYPE); // Lấy giá trị từ DB: "Import" hoặc "Export"
                    RequestType type = RequestType.valueOf(typeStr.toUpperCase()); // Convert String -> Enum
                    r.setType(type); // set đúng kiểu RequestType
                    r.setApprovedBy(udao.getUserById(rs.getInt(COL_APPROVEDBY)));
                    list.add(r);
                }
            } catch (Exception e) {
                Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
            }

            return list;
        }
    }

     public static void main(String[] args) throws SQLException {
        requestDAO rdao = new requestDAO();

//        udao.deleteStaffById(1);
        List<Request> list = rdao.pagingStaff(1);
       
        int count = rdao.getTotalRequest();
        System.out.println(count);
        for (Request r : list) {
            System.out.println(r);
        }

//        System.out.println(udao.updateUser(7, false, 2));

    }
    
}
