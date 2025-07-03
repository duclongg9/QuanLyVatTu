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
import com.qlvt.model.MaterialStatus;
/**
 *
 * @author Dell-PC
 */


public class MaterialStatusDAO {
    private Connection conn;
    private static final String COL_ID = "id";
    private static final String COL_STATUS = "status";

    public MaterialStatusDAO() {
        conn = DBConnect.getConnection();
    }

    public List<MaterialStatus> getAllStatus() {
        List<MaterialStatus> list = new ArrayList<>();
        String sql = "SELECT * FROM MaterialStatus";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MaterialStatus ms = new MaterialStatus();
                ms.setId(rs.getInt(COL_ID));
                ms.setStatus(rs.getString(COL_STATUS));
                list.add(ms);
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialStatusDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public MaterialStatus getStatusById(int id) {
        String sql = "SELECT * FROM MaterialStatus WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MaterialStatus ms = new MaterialStatus();
                    ms.setId(rs.getInt(COL_ID));
                    ms.setStatus(rs.getString(COL_STATUS));
                    return ms;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialStatusDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
