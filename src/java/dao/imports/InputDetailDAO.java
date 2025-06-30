/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.connect.DBConnect;
import dao.material.MaterialItemDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.InputDetail;
import model.RequestDetail;

/**
 *
 * @author D E L L
 */
public class InputDetailDAO {
    
    
    private static final int PAGE_SIZE = 5;

    private static final String COL_ID = "id";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_INPUTWAREHOURSEID = "inputWarehouseId";
    private static final String COL_REQUESTDETAILID = "requestDetailId";
    private static final String COL_MATERIALITEMID = "materialItemId";
    private static final String COL_PRICE = "inputPrice";
    
    InputWarehourseDAO iwdao = new InputWarehourseDAO();
    requestDetailDAO rddao = new requestDetailDAO();
    MaterialItemDAO  midao = new MaterialItemDAO();

    public void insertInputDetail(int inputWarehouseId, int requestDetailId, int materialItemId, int quantity, double inputPrice) throws SQLException {
        String sql = "INSERT INTO InputDetail (quantity, inputWarehouseId, requestDetailId, materialItemId, inputPrice) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, inputWarehouseId);
            ps.setInt(3, requestDetailId);
            ps.setInt(4, materialItemId);
            ps.setDouble(5, inputPrice);
            ps.executeUpdate();
        }
    }
    //Phân trang

    public List<InputDetail> pagingImportDetailByImportWarehouseId(int index, int inputWarehourseId) throws SQLException {
        List<InputDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM inputdetail WHERE inputWarehouseId = ? LIMIT ? OFFSET ?;";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inputWarehourseId);
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    InputDetail r = new InputDetail();
                    r.setId(rs.getInt(COL_ID));
                    r.setQuantity(rs.getInt(COL_QUANTITY));
                    r.setInputWarehourseId(iwdao.getInputWarehourseById(rs.getInt(COL_INPUTWAREHOURSEID)));
                    r.setRequestDetailId(rddao.getRequestDetailById(rs.getInt(COL_REQUESTDETAILID)));
                    r.setMaterialItemId(midao.getMaterialItemById(rs.getInt(COL_MATERIALITEMID)));
                    r.setInputPrice(rs.getDouble(COL_PRICE));
                    list.add(r);
                }
            } catch (Exception e) {
                Logger.getLogger(RequestDetail.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return list;
    }

    public int getTotalImportDetailByImportWarehouseId(int inputWarehourseId) {
        String sql = "SELECT COUNT(*) FROM inputdetail WHERE inputWarehouseId = ?;";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inputWarehourseId);
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
        InputDetailDAO iddao = new InputDetailDAO();
        List<InputDetail> ld = iddao.pagingImportDetailByImportWarehouseId(1, 1);
        for (InputDetail inputDetail : ld) {
            System.out.println(inputDetail);
        }
    }
}
