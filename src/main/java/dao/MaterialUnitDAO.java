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
import model.MaterialUnit;

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
    
    //Lấy Role theo id 
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
