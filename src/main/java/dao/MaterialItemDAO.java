package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.MaterialItem;
import model.Materials;
import model.MaterialStatus;

public class MaterialItemDAO {
    private Connection conn;
    private static final String COL_ID = "id";
    private static final String COL_MATERIAL = "materialId";
    private static final String COL_STATUS = "statusId";
    private static final String COL_QUANTITY = "quantity";

    MaterialsDAO mdao = new MaterialsDAO();
    MaterialStatusDAO msdao = new MaterialStatusDAO();

    public MaterialItemDAO() {
        conn = DBConnect.getConnection();
    }

    public MaterialItem getItemByMaterialId(int materialId) {
        String sql = "SELECT * FROM MaterialItem WHERE materialId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, materialId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MaterialItem mi = new MaterialItem();
                    mi.setId(rs.getInt(COL_ID));
                    mi.setMaterialId(mdao.getMaterialById(rs.getInt(COL_MATERIAL)));
                    mi.setStatusId(msdao.getStatusById(rs.getInt(COL_STATUS)));
                    mi.setQuantity(rs.getInt(COL_QUANTITY));
                    return mi;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createItem(int materialId, int statusId) {
        String sql = "INSERT INTO MaterialItem(materialId,statusId,quantity) VALUES(?,?,0)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, materialId);
            ps.setInt(2, statusId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int materialId, int statusId) {
        String sql = "UPDATE MaterialItem SET statusId = ? WHERE materialId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, statusId);
            ps.setInt(2, materialId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}