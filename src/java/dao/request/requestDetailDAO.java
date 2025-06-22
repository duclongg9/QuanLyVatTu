/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

/*import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;*/
import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RequestDetail;
import dao.material.MaterialsDAO;
import dao.user.UserDAO;
import dao.Supplier.SupplierDAO;

/**
 *
 * @author D E L L
 */
public class requestDetailDAO {

    requestDAO rdao = new requestDAO();
    MaterialsDAO mdao = new MaterialsDAO();
    SupplierDAO sdao = new SupplierDAO();
    

    private static final int PAGE_SIZE = 5;

    private static final String COL_ID = "id";
    private static final String COL_REQUEST = "requestId";
    private static final String COL_MATERIAL = "materialId";
    private static final String COL_SUPPLIERID = "supplierId";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_NOTE = "note";

    private Connection conn;

    public requestDetailDAO() {
        conn = DBConnect.getConnection();
    }

    public List<RequestDetail> getAllMaterialsInRequest(int id) {
        List<RequestDetail> list = new ArrayList<>();
        String sql = " SELECT *\n"
                + "FROM RequestDetail\n"
                + "WHERE requestId = ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                list.clear();
                while (rs.next()) {
                    RequestDetail r = new RequestDetail();
                    r.setId(rs.getInt(COL_ID));
                    r.setMaterialId(mdao.getMaterialsById(rs.getInt(COL_MATERIAL)));
                    r.setRequestId(rdao.getRequestById(rs.getInt(COL_REQUEST)));
                    r.setQuantity(rs.getInt(COL_QUANTITY));
                    r.setNote(rs.getString(COL_NOTE));
                    list.add(r);
                }
                return list;
            }

        } catch (SQLException ex) {

            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //Phân trang
    public List<RequestDetail> pagingRequestDetailByRequestId(int index, int requestId) throws SQLException {
        List<RequestDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM requestdetail WHERE requestId = ? LIMIT ? OFFSET ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RequestDetail r = new RequestDetail();
                    r.setId(rs.getInt(COL_ID));
                    r.setMaterialId(mdao.getMaterialsById(rs.getInt(COL_MATERIAL)));
                    r.setRequestId(rdao.getRequestById(rs.getInt(COL_REQUEST)));
                    r.setSupplierId(sdao.getSupplierById(rs.getInt(COL_SUPPLIERID)));
                    r.setQuantity(rs.getInt(COL_QUANTITY));
                    r.setNote(rs.getString(COL_NOTE));
                    list.add(r);
                }
            } catch (Exception e) {
                Logger.getLogger(RequestDetail.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return list;
    }

    public int getTotalRequestDetailByRequestId(int requestId) {
        String sql = "SELECT COUNT(*) FROM requestdetail WHERE requestId = ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public static void main(String[] args) throws SQLException {
        requestDetailDAO rdao = new requestDetailDAO();

//        udao.deleteStaffById(1);
        List<RequestDetail> list = rdao.getAllMaterialsInRequest(1);

        for (RequestDetail r : list) {
            System.out.println(r);
        }

//        System.out.println(udao.updateUser(7, false, 2));
    }

}
