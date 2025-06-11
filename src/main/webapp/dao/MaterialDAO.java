
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Material;

public class MaterialDAO {

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String sql = "SELECT m.id, m.name, m.unit, ms.status, cm.category "
                       + "FROM Materials m "
                       + "JOIN MaterialStatus ms ON m.statusId = ms.id "
                       + "JOIN CategoryMaterial cm ON m.categoryId = cm.id";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Material material = new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getString("status"),
                        rs.getString("category")
                );
                materials.add(material);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return materials;
    }

    public static void main(String[] args) {
        MaterialDAO dao = new MaterialDAO();
        List<Material> list = dao.getAllMaterials();
        for (Material m : list) {
            System.out.println(m);
        }
    }
}