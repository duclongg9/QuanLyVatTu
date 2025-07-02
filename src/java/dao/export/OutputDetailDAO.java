/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.export;

import dao.connect.DBConnect;
import dao.material.MaterialItemDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OutputDetail;
import model.RequestDetail;

public class OutputDetailDAO {
    private static final int PAGE_SIZE = 5;

    private static final String COL_ID = "id";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_OUTPUTWAREHOUSEID = "outputWarehouseId";
    private static final String COL_REQUESTDETAILID = "requestDetailId";
    private static final String COL_MATERIALITEMID = "materialItemId";

    OutputWarehourseDAO owdao = new OutputWarehourseDAO();
    dao.request.requestDetailDAO rddao = new dao.request.requestDetailDAO();
    MaterialItemDAO midao = new MaterialItemDAO();

    public void insertOutputDetail(int outputWarehouseId, int requestDetailId, int materialItemId, int quantity) throws SQLException {
        String sql = "INSERT INTO OutputDetail (quantity, outputWarehouseId, requestDetailId, materialItemId) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, outputWarehouseId);
            ps.setInt(3, requestDetailId);
            ps.setInt(4, materialItemId);
            ps.executeUpdate();
        }
    }

    public List<OutputDetail> pagingOutputDetailByOutputWarehouseId(int index, int outputWarehouseId) throws SQLException {
        List<OutputDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM OutputDetail WHERE outputWarehouseId = ? LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, outputWarehouseId);
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OutputDetail d = new OutputDetail();
                    d.setId(rs.getInt(COL_ID));
                    d.setQuantity(rs.getInt(COL_QUANTITY));
                    d.setOutputWarehourseId(owdao.getOutputWarehourseById(rs.getInt(COL_OUTPUTWAREHOUSEID)));
                    d.setRequestDetailId(rddao.getRequestDetailById(rs.getInt(COL_REQUESTDETAILID)));
                    d.setMaterialItemId(midao.getMaterialItemById(rs.getInt(COL_MATERIALITEMID)));
                    list.add(d);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(OutputDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int getTotalOutputDetailByOutputWarehouseId(int outputWarehouseId) {
        String sql = "SELECT COUNT(*) FROM OutputDetail WHERE outputWarehouseId = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, outputWarehouseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(OutputDetailDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}