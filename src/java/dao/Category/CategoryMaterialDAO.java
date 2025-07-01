/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.Category;

import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CategoryMaterial;


/**
 *
 * @author D E L L
 */
public class CategoryMaterialDAO {
     private Connection conn;

    private static final String COL_ID = "id";
    private static final String COL_CATEGORY = "category";
    private static final String COL_STATUS = "status";
    public CategoryMaterialDAO() {
        conn = DBConnect.getConnection();
    }
    
    
    
    //lấy tất cả danh mục vật tư
    public List<CategoryMaterial> getCategoryList() throws SQLException {
        List<CategoryMaterial> cateMateList = new ArrayList<>();
         String sql = "SELECT * FROM CategoryMaterial WHERE status = true";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryMaterial cm = new CategoryMaterial();
                    cm.setId(rs.getInt(COL_ID));
                    cm.setCategory(rs.getString(COL_CATEGORY));
                    cm.setStatus(rs.getBoolean(COL_STATUS));
                    cateMateList.add(cm);
                    

                }
                return cateMateList;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    
    //lấy tất cả danh mục vật tư
    public List<CategoryMaterial> getAllCategory() {
        List<CategoryMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryMaterial WHERE status = true";

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CategoryMaterial c = new CategoryMaterial();
                c.setId(rs.getInt(COL_ID));
                c.setCategory(rs.getString(COL_CATEGORY));
                c.setStatus(rs.getBoolean(COL_STATUS));
                list.add(c);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterial.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }
    
     //Lấy category theo id 
    public CategoryMaterial getCategoryById(int id) {

        String sql ="""
         SELECT * FROM CategoryMaterial
                     WHERE id =? AND status = true""";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    CategoryMaterial c = new CategoryMaterial();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategory(rs.getString(COL_CATEGORY));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    return c;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterial.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
    
}
