/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.connect.DBConnect;
import dao.material.MaterialItemDAO;
import dao.user.UserDAO;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Types;
import model.Request;
import model.RequestDetail;
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

    public boolean updateSuccessStatusRequest(int requestId) {
        String sql = "UPDATE Request SET statusId = 5 WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public boolean updateStatusRequest(int requestId, int statusId, int userId) {
    String sql = (userId == 0)
        ? "UPDATE Request SET statusId = ?, approvedBy = NULL WHERE id = ?"
        : "UPDATE Request SET statusId = ?, approvedBy = ? WHERE id = ?";

    try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, statusId);
        if (userId == 0) {
            ps.setInt(2, requestId);
        } else {
            ps.setInt(2, userId);
            ps.setInt(3, requestId);
        }
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public double getPriceForMaterialItem(int materialId, int supplierId) throws SQLException {
        String sql = "SELECT price FROM MaterialItem WHERE materialId = ? AND supplierId = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, materialId);
            ps.setInt(2, supplierId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("price");
                }
            }
        }
        return 0.0; // mặc định nếu không tìm thấy
    }

    public void updateStockQuantity(int materialId, int supplierId, int addedQuantity) throws SQLException {
        String sql = "UPDATE MaterialItem SET stockQuantity = stockQuantity + ? WHERE materialId = ? AND supplierId = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, addedQuantity);
            ps.setInt(2, materialId);
            ps.setInt(3, supplierId);
            ps.executeUpdate();
        }
    }

    public List<RequestDetail> getRequestDetailByRequestId(int requestId) throws SQLException {
        List<RequestDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM RequestDetail WHERE requestId = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RequestDetail detail = new RequestDetail();
                    detail.setId(rs.getInt("id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setNote(rs.getString("note"));

                    MaterialItemDAO midao = new MaterialItemDAO();

                    detail.setMaterialItem(midao.getMaterialItemById(rs.getInt("materialItemId")));

                    // Gán requestId nếu model có:
                    // requestDAO rdao = new requestDAO();
                    // detail.setRequestId(rdao.getRequestById(requestId)); // nếu cần object Request
                    list.add(detail);
                }
            }
        }
        return list;
    }

    public void updateRequestDetail(int detailId, int quantity, String note) throws SQLException {
        String sql = "UPDATE RequestDetail SET quantity = ?, note = ? WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);  // cập nhật quantity
            ps.setString(2, note);   // cập nhật note
            ps.setInt(3, detailId);  // biết dòng nào cần update

            ps.executeUpdate(); // chạy lệnh update
        }
    }

    // Thêm request mới, trả về requestId vừa tạo
    public int insertRequest(Connection conn, int userId, String note, Integer approverId, RequestType type) throws SQLException {
        String sql = "INSERT INTO Request (date, statusId, userId, note, type, approvedBy) VALUES (NOW(), ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, 1); // statusId: 1 = Pending
            ps.setInt(2, userId);
            ps.setString(3, note);
            ps.setString(4, type.name());

            if (approverId != null) {
                ps.setInt(5, approverId);
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            int affectedRows = ps.executeUpdate(); // execute

            if (affectedRows == 0) {
                throw new SQLException("Creating request failed, no rows affected.");
            }

            // Lấy ID của request mới tạo
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về requestId vừa tạo
                } else {
                    throw new SQLException("Creating request failed, no ID obtained.");
                }
            }
        }
    }

    // Thêm chi tiết các vật tư cho Request vào bảng RequestDetail
    public void insertRequestDetail(Connection conn, int requestId, int materialItemId, int quantity, String note) throws SQLException {
        String sql = "INSERT INTO RequestDetail (requestId, materialItemId, quantity, note) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ps.setInt(2, materialItemId); // chỉ cần id dòng MaterialItem
            ps.setInt(3, quantity);
            ps.setString(4, note);
            ps.executeUpdate();
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
