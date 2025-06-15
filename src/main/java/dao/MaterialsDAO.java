/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Materials;
/**
 *
 * @author D E L L
 */
public class MaterialsDAO {
    private Connection conn;
    
    MaterialUnitDAO mudao = new MaterialUnitDAO();
    CategoryMaterialDAO cmdao = new CategoryMaterialDAO();

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_UNIT = "unitId";
    private static final String COL_IMAGE = "image";
    private static final String COL_CATEGORY = "categoryId";
    
     private static final int PAGE_SIZE = 10;

    public MaterialsDAO(Connection conn) {
        this.conn = conn;
    }
    
    public MaterialsDAO(){
        this(DBConnect.getConnection());
    }
    
    public List<Materials> getAllMaterial() {
        List<Materials> list = new ArrayList<>();
        String sql = " SELECT * FROM Materials ORDER BY id DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                list.clear();
                while (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setCategoryId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    list.add(m);
                }
                return list;
            }

        } catch (SQLException ex) {

            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
       //đếm số lượng vật tư trong database
    public int getTotalMaterials() {
        String sql = "SELECT COUNT(*) FROM Materials;";
        try (PreparedStatement ps = conn.prepareStatement(sql);) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    //Phân trang
    public List<Materials> pagingMaterials(int index) throws SQLException {
        List<Materials> list = new ArrayList<>();
        String sql = "SELECT * FROM Materials\n"
                + "LIMIT ? OFFSET ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                   Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setCategoryId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    list.add(m);
                }
            } catch (Exception e) {
                Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
            }

            return list;
        }
    }


    // Lấy vật tư theo id
    public Materials getMaterialById(int id) {
        String sql = "SELECT * FROM Materials WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setCategoryId(cmdao.getCategoryById(rs.getInt(COL_CATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    return m;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm mới vật tư
    public int addMaterial(String name, int unitId, String image, int categoryId) {
        String sql = "INSERT INTO Materials(name, unitId, image, categoryId) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, unitId);
            ps.setString(3, image);
            ps.setInt(4, categoryId);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Cập nhật vật tư
    public void updateMaterial(int id, String name, int unitId, String image, int categoryId) {
        String sql = "UPDATE Materials SET name=?, unitId=?, image=?, categoryId=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, unitId);
            ps.setString(3, image);
            ps.setInt(4, categoryId);
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa mềm vật tư thông qua MaterialItem
    public void deleteMaterial(int id) {
        MaterialItemDAO midao = new MaterialItemDAO();
        // 2 được giả định là trạng thái "Đã xóa" trong bảng MaterialStatus
        midao.updateStatus(id, 2);
    }
     public static void main(String[] args) throws SQLException {
        MaterialsDAO mdao = new MaterialsDAO();

//        udao.deleteStaffById(1);
        List<Materials> list = mdao.pagingMaterials(1);
       
        int count = mdao.getTotalMaterials();
        System.out.println(count);
        for (Materials m : list) {
            System.out.println(m);
        }

//        System.out.println(udao.updateUser(7, false, 2));

    }
}
