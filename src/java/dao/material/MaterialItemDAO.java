/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.material;

import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CategoryMaterial;
import model.MaterialItem;

/**
 *
 * @author D E L L
 */
public class MaterialItemDAO {

    private Connection conn;

    private static final String COL_ID = "id";
    private static final String COL_STATUSID = "statusId";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_MATERIALSUPPLIERID = "materials_SupplierId";

    public MaterialItemDAO() {
        conn = DBConnect.getConnection();
    }

    MaterialStatusDAO msdao = new MaterialStatusDAO();
    MaterialSupplierDAO msldao = new MaterialSupplierDAO();

    public MaterialItem getMaterialItemById(int materialItemId) {
        String sql = "SELECT * FROM MaterialItem Where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, materialItemId);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    MaterialItem mi = new MaterialItem();
                    mi.setId(rs.getInt(COL_ID));
                    mi.setStarusId(msdao.getMaterialStatusById(rs.getInt(COL_STATUSID)));
                    mi.setQuantity(rs.getInt(COL_QUANTITY));
                    mi.setMaterialSupplier(msldao.getMaterialSupplierById(rs.getInt(COL_MATERIALSUPPLIERID)));
                    return mi;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialItem.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    public List<MaterialItem> filterMaterial(int categoryMaterialId, int subCategoryId, String keyword) {
        List<MaterialItem> materialList = new ArrayList<>();
        String sql = """
        SELECT mi.*
        FROM MaterialItem mi
        JOIN materials_Supplier ms ON mi.materials_SupplierId = ms.id
        JOIN Materials m ON ms.materialId = m.id
        JOIN SubCategory sc ON m.subCategoryId = sc.id
        JOIN CategoryMaterial cm ON sc.categoryMaterialId = cm.id
        WHERE (? = 0 OR cm.id = ?)
          AND (? = 0 OR sc.id = ?)
          AND m.name LIKE ?;
    """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryMaterialId);
            ps.setInt(2, categoryMaterialId);
            ps.setInt(3, subCategoryId);
            ps.setInt(4, subCategoryId);
            ps.setString(5, "%" + (keyword == null ? "" : keyword.trim()) + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MaterialItem mi = new MaterialItem();
                    mi.setId(rs.getInt(COL_ID));
                    mi.setStarusId(msdao.getMaterialStatusById(rs.getInt(COL_STATUSID)));
                    mi.setQuantity(rs.getInt(COL_QUANTITY));
                    mi.setMaterialSupplier(msldao.getMaterialSupplierById(rs.getInt(COL_MATERIALSUPPLIERID)));
                    materialList.add(mi);
                }
            }
            return materialList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
