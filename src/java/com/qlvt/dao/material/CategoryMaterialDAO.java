/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.dao.material;

import com.qlvt.dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qlvt.model.CategoryMaterial;


/**
 *
 * @author D E L L
 */
public class CategoryMaterialDAO {
//     private Connection conn;

    public static final int PAGE_SIZE = 7;
    private static final String COL_ID = "id";
    private static final String COL_CATEGORY = "category";
    private static final String COL_STATUS = "status";

    public CategoryMaterialDAO() {
//        conn = DBConnect.getConnection();
    }
    
    //lấy tất cả danh mục vật tư
    public List<CategoryMaterial> getAllCategory() {
        List<CategoryMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM ql_vat_tu.categorymaterial where status = true";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
                     SELECT *
                    FROM CategoryMaterial
                    WHERE id = ? AND status = true
                    """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
    
     //lấy tất cả danh mục vật tư
    public List<CategoryMaterial> getCategoryList() throws SQLException {
        List<CategoryMaterial> cateMateList = new ArrayList<>();
         String sql = "SELECT * FROM ql_vat_tu.categorymaterial WHERE status = true";
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
    

    
    // Tạo mới danh mục vật tư
    public int createCategory(String category) {
        String sql = "INSERT INTO CategoryMaterial(category) VALUES(?)";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Cập nhật danh mục vật tư
    public int updateCategory(int id, String category) {
        String sql = "UPDATE CategoryMaterial SET category=? WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    // Delete category by id
    public int deleteCategory(int id) {
        String sql = "UPDATE CategoryMaterial SET status = false WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Count categories
    public int getTotalCategories() {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE status = true";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Pagination for categories
    public List<CategoryMaterial> pagingCategories(int index) {
        List<CategoryMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryMaterial WHERE status = true ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryMaterial c = new CategoryMaterial();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategory(rs.getString(COL_CATEGORY));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Paging deleted categories
    public List<CategoryMaterial> pagingDeletedCategories(int index) {
        List<CategoryMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryMaterial WHERE status = false ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryMaterial c = new CategoryMaterial();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategory(rs.getString(COL_CATEGORY));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Search categories by name with pagination
    public List<CategoryMaterial> searchCategoriesByName(String name, int index) {
        List<CategoryMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryMaterial WHERE status = true AND category LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryMaterial c = new CategoryMaterial();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategory(rs.getString(COL_CATEGORY));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Count categories when searching by name
    public int getTotalCategoriesByName(String name) {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE status = true AND category LIKE ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    public int getTotalDeletedCategories() {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE status = false";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // restore category
    public void activateCategory(int id) {
        String sql = "UPDATE CategoryMaterial SET status = true WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Check duplicate category name
    public boolean isNameExists(String name, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE LOWER(category) = LOWER(?)";
        if (excludeId != null) {
            sql += " AND id <> ?";
        }
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            if (excludeId != null) {
                ps.setInt(2, excludeId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
