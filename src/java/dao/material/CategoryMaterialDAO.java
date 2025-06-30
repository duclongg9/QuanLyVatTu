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


/**
 *
 * @author D E L L
 */
public class CategoryMaterialDAO {
     private Connection conn;

    public static final int PAGE_SIZE = 7;
    private static final String COL_ID = "id";
    private static final String COL_CATEGORY = "category";

    public CategoryMaterialDAO() {
        conn = DBConnect.getConnection();
    }
    
    //lấy tất cả danh mục vật tư
    public List<CategoryMaterial> getAllCategory() {
        List<CategoryMaterial> list = new ArrayList<>();
        String sql = "SELECT * FROM categorymaterial";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CategoryMaterial c = new CategoryMaterial();
                c.setId(rs.getInt(COL_ID));
                c.setCategory(rs.getString(COL_CATEGORY));
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
    
    
    // Tạo mới danh mục vật tư
    public int createCategory(String category) {
        String sql = "INSERT INTO CategoryMaterial(category) VALUES(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String sql = "DELETE FROM CategoryMaterial WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryMaterialDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Count categories
    public int getTotalCategories() {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        String sql = "SELECT * FROM CategoryMaterial ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryMaterial c = new CategoryMaterial();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategory(rs.getString(COL_CATEGORY));
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
        String sql = "SELECT * FROM CategoryMaterial WHERE category LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoryMaterial c = new CategoryMaterial();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategory(rs.getString(COL_CATEGORY));
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
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE category LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
}
