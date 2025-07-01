/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.Category;

import dao.connect.DBConnect;
import dao.material.CategoryMaterialDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Materials;
import model.SubCategory;
import model.Supplier;

/**
 *
 * @author D E L L
 */
public class SubCategoryDAO {
    private Connection conn;

    private static final String COL_ID = "id";
    private static final String COL_SUBCATEGORYNAME = "subCategoryName";
    private static final String COL_CATEGORYMATERIALID = "categoryMaterialId";
    private static final String COL_STATUS = "status";
    
    CategoryMaterialDAO cmdao = new CategoryMaterialDAO();

   
    public SubCategoryDAO() {
       
    }
    
    public SubCategory getSubCategoryById(int subCategoryId){
        String sql = "SELECT * FROM subcategory WHERE id = ? AND status = true";
        try (Connection conn = DBConnect.getConnection();PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, subCategoryId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                     SubCategory m = new SubCategory();
                    m.setId(rs.getInt(COL_ID));
                    m.setSubCategoryName(rs.getString(COL_SUBCATEGORYNAME));
                    m.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORYMATERIALID)));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    return m;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Materials.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    
    public List<SubCategory> getSubCategoryByCatId(int categoryMaterialId){
        List<SubCategory> subCatList = new ArrayList<>();
        String sql="SELECT * FROM subcategory WHERE status = true ";
        if(categoryMaterialId != 0){
            sql+="AND categoryMaterialId = ?";
        }
       try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
           if(categoryMaterialId != 0){
            ps.setInt(1, categoryMaterialId);
           }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubCategory m = new SubCategory();
                    m.setId(rs.getInt(COL_ID));
                    m.setSubCategoryName(rs.getString(COL_SUBCATEGORYNAME));
                    m.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORYMATERIALID)));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    subCatList.add(m);
                }
                return subCatList;
            }

        } catch (Exception e) {
            Logger.getLogger(Materials.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    
}
