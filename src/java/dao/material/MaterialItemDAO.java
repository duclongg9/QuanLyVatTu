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
    private static final String COL_STATUS_ID = "statusId";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_MATERIALSUPPLIER_ID = "materials_SupplierId";

    public MaterialItemDAO() {
        conn = DBConnect.getConnection();
    }

    MaterialStatusDAO msdao = new MaterialStatusDAO();
    MaterialSupplierDAO msldao = new MaterialSupplierDAO();
    

    
      public List<MaterialItem> getAllMaterialItem() {
        List<MaterialItem> list = new ArrayList<>();
        String sql = "SELECT * FROM MaterialItem";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MaterialItem item = new MaterialItem();
                item.setId(rs.getInt(COL_ID));
                item.setStatusId(msdao.getStatusById(rs.getInt(COL_STATUS_ID)));
                item.setQuantity(rs.getInt(COL_QUANTITY));
                item.setMaterialsSupplierId(msldao.getById(rs.getInt(COL_MATERIALSUPPLIER_ID)));
                list.add(item);
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialItemDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public MaterialItem getMaterialItemById(int id) {
        String sql = "SELECT * FROM MaterialItem WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MaterialItem item = new MaterialItem();
                    item.setId(rs.getInt(COL_ID));
                    item.setStatusId(msdao.getStatusById(rs.getInt(COL_STATUS_ID)));
                    item.setQuantity(rs.getInt(COL_QUANTITY));
                    item.setMaterialsSupplierId(msldao.getById(rs.getInt(COL_MATERIALSUPPLIER_ID)));
                    return item;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialItemDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public boolean increaseQuantityByMaterialItemId(int materialItemId, int addedQuantity) {
    String sql = "UPDATE MaterialItem SET quantity = quantity + ? WHERE id = ?";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, addedQuantity);
        ps.setInt(2, materialItemId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;

    } catch (Exception e) {
        Logger.getLogger(MaterialItem.class.getName()).log(Level.SEVERE, null, e);
    }

    return false;
}
    public boolean decreaseQuantityByMaterialItemId(int materialItemId, int deductedQuantity) {
        String sql = "UPDATE MaterialItem SET quantity = quantity - ? WHERE id = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deductedQuantity);
            ps.setInt(2, materialItemId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            Logger.getLogger(MaterialItem.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
    
    public double getPriceByMaterialItemId(int materialItemId) {
    String sql = """
                SELECT ms.price
                FROM MaterialItem mi
                JOIN materials_Supplier ms ON mi.materials_SupplierId = ms.id
                WHERE mi.id = ?
                """;

    try (Connection conn = DBConnect.getConnection();  
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, materialItemId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("price"); 
            }
        }
    } catch (Exception e) {
        Logger.getLogger(MaterialItem.class.getName()).log(Level.SEVERE, null, e);
    }

    return 0; 
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
                    mi.setStarusId(msdao.getStatusById(rs.getInt(COL_STATUS_ID)));
                    mi.setQuantity(rs.getInt(COL_QUANTITY));
                    mi.setMaterialSupplier(msldao.getMaterialSupplierById(rs.getInt(COL_MATERIALSUPPLIER_ID)));
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
