/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.subcategory;

import dao.connect.DBConnect;
import dao.material.CategoryMaterialDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SubCategory;
/**
 *
 * @author Dell-PC
 */
public class SubCategoryDAO {
private Connection conn;
    public static final int PAGE_SIZE = 7;
    CategoryMaterialDAO cmdao = new CategoryMaterialDAO();
    private static final String COL_ID = "id";
    private static final String COL_NAME = "subCategoryName";
    private static final String COL_CATEGORY_ID = "categoryMaterialId";
    private static final String COL_STATUS = "status";

    public SubCategoryDAO() {
        conn = DBConnect.getConnection();
    }

    public List<SubCategory> getAllSubCategory() {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM SubCategory WHERE status = true";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SubCategory sc = new SubCategory();
                sc.setId(rs.getInt(COL_ID));
                sc.setSubCategoryName(rs.getString(COL_NAME));
                sc.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY_ID)));
                list.add(sc);
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public SubCategory getSubCategoryById(int id) {
        String sql = "SELECT * FROM SubCategory WHERE id=? AND status = true";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SubCategory sc = new SubCategory();
                    sc.setId(rs.getInt(COL_ID));
                    sc.setSubCategoryName(rs.getString(COL_NAME));
                    sc.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY_ID)));
                    sc.setStatus(rs.getBoolean(COL_STATUS));
                    return sc;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    public int createSubCategory(String name, int categoryId) {
        String sql = "INSERT INTO SubCategory(subCategoryName, categoryMaterialId) VALUES(?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public int updateSubCategory(int id, String name, int categoryId) {
        String sql = "UPDATE SubCategory SET subCategoryName=?, categoryMaterialId=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            ps.setInt(3, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Delete subcategory
    public int deleteSubCategory(int id) {
        String sql = "UPDATE SubCategory SET status = false WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Deactivate all subcategories under a category
    public void deactivateByCategoryId(int categoryId) {
        String sql = "UPDATE SubCategory SET status = false WHERE categoryMaterialId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    // Count subcategories
    public int getTotalSubCategory() {
         String sql = "SELECT COUNT(*) FROM SubCategory WHERE status = true";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Pagination for subcategories
    public List<SubCategory> pagingSubCategory(int index) {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM SubCategory WHERE status = true ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubCategory sc = new SubCategory();
                    sc.setId(rs.getInt(COL_ID));
                    sc.setSubCategoryName(rs.getString(COL_NAME));
                    sc.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY_ID)));
                    sc.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(sc);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Search subcategory by name
    public List<SubCategory> searchSubCategoryByName(String name, int index) {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM SubCategory WHERE status = true AND subCategoryName LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubCategory sc = new SubCategory();
                    sc.setId(rs.getInt(COL_ID));
                    sc.setSubCategoryName(rs.getString(COL_NAME));
                    sc.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY_ID)));
                     sc.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(sc);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int getTotalSubCategoryByName(String name) {
        String sql = "SELECT COUNT(*) FROM SubCategory WHERE status = true AND subCategoryName LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Search subcategory by category id
    public List<SubCategory> searchSubCategoryByCategory(int categoryId, int index) {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM SubCategory WHERE status = true AND categoryMaterialId=? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SubCategory sc = new SubCategory();
                    sc.setId(rs.getInt(COL_ID));
                    sc.setSubCategoryName(rs.getString(COL_NAME));
                    sc.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY_ID)));
                    sc.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(sc);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public int getTotalSubCategoryByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM SubCategory WHERE status = true AND categoryMaterialId=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SubCategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}