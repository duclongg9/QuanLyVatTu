/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.materialitem;

import dao.connect.DBConnect;
import dao.material.MaterialSupplierDAO;
import dao.material.MaterialsDAO;
import dao.material.MaterialStatusDAO;
import dao.material.MaterialStatusDAO;
import dao.material.MaterialSupplierDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MaterialItem;
/**
 *
 * @author Dell-PC
 */
public class MaterialItemDAO {
 private Connection conn;
    MaterialStatusDAO msdao = new MaterialStatusDAO();
    MaterialSupplierDAO msupplierDAO = new MaterialSupplierDAO();
    private static final String COL_ID = "id";
    private static final String COL_STATUS_ID = "statusId";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_MATERIALSUPPLIER_ID = "materials_SupplierId";

    public MaterialItemDAO() {
        conn = DBConnect.getConnection();
    }

    public List<MaterialItem> getAllMaterialItem() {
        List<MaterialItem> list = new ArrayList<>();
        String sql = "SELECT * FROM MaterialItem";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MaterialItem item = new MaterialItem();
                item.setId(rs.getInt(COL_ID));
                item.setStatusId(msdao.getStatusById(rs.getInt(COL_STATUS_ID)));
                item.setQuantity(rs.getInt(COL_QUANTITY));
                item.setMaterialSupplier(msupplierDAO.getById(rs.getInt(COL_MATERIALSUPPLIER_ID)));
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
                    item.setMaterialSupplier(msupplierDAO.getById(rs.getInt(COL_MATERIALSUPPLIER_ID)));
                    return item;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialItemDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
