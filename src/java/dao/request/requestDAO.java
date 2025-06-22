/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;      
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.util.Types;
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

    // Thêm request mới, trả về requestId vừa tạo
    public int insertRequest(Connection conn, int userId, String note, Integer approverId) throws SQLException {
        String sql = "INSERT INTO Request (date, statusId, userId, note, type, approvedBy) VALUES (NOW(), ?, ?, ?, 'Import', ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, 1); // statusId (đang mặc định là 1-pending)
            ps.setInt(2, userId);
            ps.setString(3, note);
            if (approverId != null) {
                ps.setInt(4, approverId);
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // trả về requestId mới tạo
                }
            }
        }
        return -1; // lỗi
    }
    
    // Thêm chi tiết các vật tư cho Request vào bảng RequestDetail
    public void insertRequestDetails(Connection conn, int requestId, Map<Integer, Integer> materialQuantityMap) throws SQLException {
        String sql = "INSERT INTO RequestDetail (requestId, materialId, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Map.Entry<Integer, Integer> entry : materialQuantityMap.entrySet()) {
                ps.setInt(1, requestId);
                ps.setInt(2, entry.getKey()); // materialId
                ps.setInt(3, entry.getValue()); // quantity
                ps.addBatch();
            }
            ps.executeBatch(); // chạy 1 lần cho tất cả
        }
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
    
    public Request getRequestById(int id) {

        String sql = "SELECT * FROM ql_vat_tu.request where id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) { //Sử dụng try-with-Resource để đóng tài nguyên sau khi sử dụng

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
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
                    return r;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Request.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
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
