/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.Supplier;

import dao.connect.DBConnect;
import dao.role.RoleDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Materials;
import model.Supplier;

/**
 *
 * @author D E L L
 */
public class SupplierDAO {

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_PHONE = "phone";
    private static final String COL_ADDRESS = "address";
    

    private Connection conn;

    RoleDAO rdao = new RoleDAO();

  

    public Supplier getSupplierById(int id) {

        String sql = "SELECT * FROM Supplier WHERE id = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Supplier m = new Supplier();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setPhone(rs.getString(COL_PHONE));
                    m.setAddress(rs.getString(COL_ADDRESS));
                    return m;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Materials.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
