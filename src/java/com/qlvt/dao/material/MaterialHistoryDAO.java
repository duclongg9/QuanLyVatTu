/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dell-PC
 */
package com.qlvt.dao.material;

import com.qlvt.dao.connect.DBConnect;
import com.qlvt.dao.subcategory.SubCategoryDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.qlvt.dao.material.MaterialHistoryDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qlvt.model.MaterialHistory;
import com.qlvt.model.MaterialUnit;
import com.qlvt.model.Materials;

public class MaterialHistoryDAO {
    private MaterialUnitDAO unitDAO = new MaterialUnitDAO();
    private SubCategoryDAO subDAO = new SubCategoryDAO();
    private MaterialsDAO materialsDAO = new MaterialsDAO();

    private static final String COL_ID = "id";
    private static final String COL_MATERIAL = "materialId";
    private static final String COL_NAME = "name";
    private static final String COL_UNIT = "unitId";
    private static final String COL_IMAGE = "image";
    private static final String COL_SUBCATEGORY = "subCategoryId";
    private static final String COL_TIME = "archivedAt";

    public int insertHistory(Materials m) {
        String sql = "INSERT INTO MaterialHistory(materialId, name, unitId, image, subCategoryId) VALUES(?,?,?,?,?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getId());
            ps.setString(2, m.getName());
            ps.setInt(3, m.getUnitId().getId());
            ps.setString(4, m.getImage());
            ps.setInt(5, m.getSubCategoryId().getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(MaterialHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public List<MaterialHistory> getAllHistory() {
        List<MaterialHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM MaterialHistory ORDER BY archivedAt DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MaterialHistory h = new MaterialHistory();
                h.setId(rs.getInt(COL_ID));
                h.setMaterial(materialsDAO.getMaterialsById(rs.getInt(COL_MATERIAL)));
                h.setName(rs.getString(COL_NAME));
                h.setUnit(unitDAO.getUnitById(rs.getInt(COL_UNIT)));
                h.setImage(rs.getString(COL_IMAGE));
                h.setSubCategory(subDAO.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                h.setArchivedAt(rs.getTimestamp(COL_TIME));
                list.add(h);
            }
        } catch (SQLException e) {
            Logger.getLogger(MaterialHistoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }
}