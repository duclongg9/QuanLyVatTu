/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public CategoryMaterialDAO() {
        conn = DBConnect.getConnection();
    }
    
     //Lấy category theo id 
    public CategoryMaterial getCategoryById(int id) {

        String sql ="""
                     SELECT * 
                     FROM categorymaterial 
                     WHERE id =?
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    CategoryMaterial c = new CategoryMaterial();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategory(rs.getString(COL_CATEGORY));
                    return c;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterial.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
    
}
