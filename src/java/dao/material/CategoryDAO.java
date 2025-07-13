/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.material;

import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;


/**
 *
 * @author D E L L
 */
public class CategoryDAO {
     private Connection conn;

    public static final int PAGE_SIZE = 7;
    private static final String COL_ID = "id";
    private static final String COL_CATEGORY = "category";
    private static final String COL_STATUS = "status";

    public CategoryDAO() {
        conn = DBConnect.getConnection();
    }
    
    //lấy tất cả danh mục vật tư
    public List<Category> getAllCategory() {
         List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = true";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setCategoryName(rs.getString("categoryName"));
                c.setParentCateId(rs.getInt("parentCateId"));
                c.setStatus(rs.getBoolean("status"));
                list.add(c);
            }
        } catch (Exception e) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }
    
     //Lấy category theo id 
    public Category getCategoryById(int id) {

        String sql ="""
                     SELECT *
                    FROM CategoryMaterial
                    WHERE id = ? AND status = true
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    return c;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
     //lấy tất cả danh mục vật tư
    public List<Category> getCategoryList() throws SQLException {
        List<Category> cateMateList = new ArrayList<>();
         String sql = "SELECT * FROM ql_vat_tu.categorymaterial WHERE status = true";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category cm = new Category();
                    cm.setId(rs.getInt(COL_ID));
                    cm.setCategoryName(rs.getString(COL_CATEGORY));
                    cm.setParentCateId(rs.getInt("parentCateId"));
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
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    // Delete category by id
    public int deleteCategory(int id) {
        String sql = "UPDATE CategoryMaterial SET status = false WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Count categories
    public int getTotalCategories() {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE status = true";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Pagination for categories
    public List<Category> pagingCategories(int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryMaterial WHERE status = true ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Paging deleted categories
    public List<Category> pagingDeletedCategories(int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryMaterial WHERE status = false ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Search categories by name with pagination
    public List<Category> searchCategoriesByName(String name, int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM CategoryMaterial WHERE status = true AND category LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Count categories when searching by name
    public int getTotalCategoriesByName(String name) {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE status = true AND category LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
    
    public int getTotalDeletedCategories() {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE status = false";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // restore category
    public void activateCategory(int id) {
        String sql = "UPDATE CategoryMaterial SET status = true WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Check duplicate category name
    public boolean isNameExists(String name, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM CategoryMaterial WHERE LOWER(category) = LOWER(?)";
        if (excludeId != null) {
            sql += " AND id <> ?";
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }
}
