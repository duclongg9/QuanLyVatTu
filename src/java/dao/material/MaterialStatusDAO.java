/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.material;

import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CategoryMaterial;
import model.MaterialStatus;

/**
 *
 * @author D E L L
 */
public class MaterialStatusDAO {
   private Connection conn;
    
    private static final String COL_ID = "id";
    private static final String COL_STATUS = "status";

    public MaterialStatusDAO() {
        conn = DBConnect.getConnection();
    }
    
    public MaterialStatus getMaterialStatusById(int materialStatusId){
        String sql = """
                     SELECT * 
                     FROM MaterialStatus 
                     WHERE id =?
                    """;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, materialStatusId);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    MaterialStatus ms = new MaterialStatus();
                    ms.setId(rs.getInt(COL_ID));
                    ms.setStatus(rs.getString(COL_STATUS));
                    return ms;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialStatus.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
}
