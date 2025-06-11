/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CategoryMaterial;

/**
 *
 * @author DELL
 */
public class CategoryMaterialDAO {
    
      Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<CategoryMaterial> getAll() {
        List<CategoryMaterial> list = new ArrayList<>();
        String query = "select * from CategoryMaterial";
        try {
            conn = new DBConnect().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CategoryMaterial(rs.getInt(1),
                        rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    
     public static void main(String[] args) {
        CategoryMaterialDAO dao = new CategoryMaterialDAO();
        List<CategoryMaterial> list = dao.getAll();

        for (CategoryMaterial cm : list) {
            System.out.println(cm);
        } 
    }
}
