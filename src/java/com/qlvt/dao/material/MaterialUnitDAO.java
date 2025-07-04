/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.dao.material;

import com.qlvt.dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qlvt.model.MaterialUnit;

/**
 *
 * @author D E L L
 */
public class MaterialUnitDAO {
    private Connection conn;

    private static final String COL_ID = "id";
    private static final String COL_UNIT = "unit";

    public MaterialUnitDAO() {
        conn = DBConnect.getConnection();
    }
    
    //lấy tất cả đơn vị vật tư
    public List<MaterialUnit> getAllUnit() {
        List<MaterialUnit> list = new ArrayList<>();
        String sql = "SELECT * FROM materialunit";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MaterialUnit m = new MaterialUnit();
                m.setId(rs.getInt(COL_ID));
                m.setUnitName(rs.getString(COL_UNIT));
                list.add(m);
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialUnit.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }
   //lấy vật tư theo id
    public MaterialUnit getUnitById(int id) {

        String sql ="""
                     SELECT * 
                     FROM materialunit 
                     WHERE id =?
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    MaterialUnit m = new MaterialUnit();
                    m.setId(rs.getInt(COL_ID));
                    m.setUnitName(rs.getString(COL_UNIT));
                    return m;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialUnit.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
}
