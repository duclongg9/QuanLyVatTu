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
    CategoryMaterialDAO cmdao = new CategoryMaterialDAO();
    private static final String COL_ID = "id";
    private static final String COL_NAME = "subCategoryName";
    private static final String COL_CATEGORY_ID = "categoryMaterialId";

    public SubCategoryDAO() {
        conn = DBConnect.getConnection();
    }

    public List<SubCategory> getAllSubCategory() {
        List<SubCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM SubCategory";
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
        String sql = "SELECT * FROM SubCategory WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SubCategory sc = new SubCategory();
                    sc.setId(rs.getInt(COL_ID));
                    sc.setSubCategoryName(rs.getString(COL_NAME));
                    sc.setCategoryMaterialId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY_ID)));
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
}