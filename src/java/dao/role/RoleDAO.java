/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.User;
import dao.connect.DBConnect;

/**
 *
 * @author D E L L
 */
public class RoleDAO {

    private Connection conn;

    private static final String COL_ID = "id";
    private static final String COL_ROLE = "role";

    public RoleDAO() {
        conn = DBConnect.getConnection();
    }

    //Lấy Role theo id 
    public Role getRoleById(int id) {

        String sql ="""
                     SELECT * 
                     FROM role 
                     WHERE id =?
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Role r = new Role();
                    r.setId(rs.getInt(COL_ID));
                    r.setRoleName(rs.getString(COL_ROLE));
                    return r;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    //lấy tất cả role
    public List<Role> getAllRole() {
        List<Role> lr = new ArrayList<>();
        
        String sql = "SELECT * FROM ql_vat_tu.role";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                
                while (rs.next()) {
                    Role r = new Role();
                    if (rs.getInt(COL_ID) != 1) { //trừ ADMIN ra 
                        r.setId(rs.getInt(COL_ID));
                        r.setRoleName(rs.getString(COL_ROLE));
                        lr.add(r);
                    }

                }
                return lr;
            }
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
     public List<Role> getAllRoleExeptedCEO() {
        List<Role> lr = new ArrayList<>();
        
        String sql = "SELECT * FROM ql_vat_tu.role";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                
                while (rs.next()) {
                    Role r = new Role();
                    if (rs.getInt(COL_ID) != 1 && rs.getInt(COL_ID) != 4) { //trừ ADMIN ra 
                        r.setId(rs.getInt(COL_ID));
                        r.setRoleName(rs.getString(COL_ROLE));
                        lr.add(r);
                    }

                }
                return lr;
            }
        } catch (Exception e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    public static void main(String[] args) {
        RoleDAO rdao = new RoleDAO();
//
//        udao.deleteStaffById(1);
        List<Role> list = rdao.getAllRole();
//       
//        int count = udao.getTotalStaff();
//        System.out.println(count);
        for (Role r : list) {
            System.out.println(r);
        }
    }
}