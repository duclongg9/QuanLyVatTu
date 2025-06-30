/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.material;
import dao.connect.DBConnect;
import dao.request.requestDAO;
import dao.user.UserDAO;
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
//    private Connection conn;
    
    MaterialUnitDAO mudao = new MaterialUnitDAO();
    dao.subcategory.SubCategoryDAO scdao = new dao.subcategory.SubCategoryDAO();

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_UNIT = "unitId";
    private static final String COL_IMAGE = "image";
    private static final String COL_SUBCATEGORY = "subCategoryId";
    private static final String COL_CREATED = "createdAt";
    private static final String COL_UPDATED = "updatedAt";
    private static final String COL_REPLACEMENT = "replacementMaterialId";
    private static final String COL_STATUS = "status";
    private static final int PAGE_SIZE = 7;
     

    public MaterialsDAO() {
    }
    
//    public MaterialsDAO(){
//        this(DBConnect.getConnection());
//    }
    
    public List<Materials> getAllMaterial() {
        List<Materials> list = new ArrayList<>();
        String sql = " SELECT * FROM Materials WHERE status = true ORDER BY id DESC";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                list.clear();
                while (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    m.setCreatedAt(rs.getTimestamp(COL_CREATED));
                    m.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
                    int rep = rs.getInt(COL_REPLACEMENT);
                    if(rep > 0) {
                        m.setReplacementMaterialId(getMaterialsById(rep));
                    }
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
        String sql = "SELECT COUNT(*) FROM Materials WHERE status = true;";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM Materials WHERE status = true LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                   Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    m.setCreatedAt(rs.getTimestamp(COL_CREATED));
                    m.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
                    int repDel = rs.getInt(COL_REPLACEMENT);
                    if(repDel > 0) {
                        m.setReplacementMaterialId(getMaterialsById(repDel));
                    }
                    list.add(m);
                }
            } catch (Exception e) {
                Logger.getLogger(requestDAO.class.getName()).log(Level.SEVERE, null, e);
            }

            return list;
        }
    }
    
    // Search materials by name with pagination
    public List<Materials> searchMaterialsByName(String name, int index) throws SQLException {
        List<Materials> list = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE status = true AND name LIKE ? LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    m.setCreatedAt(rs.getTimestamp(COL_CREATED));
                    m.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
                    int repDel2 = rs.getInt(COL_REPLACEMENT);
                    if(repDel2 > 0) {
                        m.setReplacementMaterialId(getMaterialsById(repDel2));
                    }
                    list.add(m);
                }
            }
        }
        return list;
    }

//     Count materials when searching by name
    public int getTotalMaterialsByName(String name) {
        String sql = "SELECT COUNT(*) FROM Materials WHERE status = true AND name LIKE ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

// Get materials by category with pagination
    public List<Materials> searchMaterialsByCategory(int categoryId, int index) throws SQLException {
        List<Materials> list = new ArrayList<>();
        String sql = "SELECT m.* FROM Materials m JOIN SubCategory sc ON m.subCategoryId = sc.id WHERE m.status = true AND sc.categoryMaterialId = ? LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    m.setCreatedAt(rs.getTimestamp(COL_CREATED));
                    m.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
                    int rep = rs.getInt(COL_REPLACEMENT);
                    if (rep > 0) {
                        m.setReplacementMaterialId(getMaterialsById(rep));
                    }
                    list.add(m);
                }
            }
        }
        return list;
    }

    // Count materials by category
    public int getTotalMaterialsByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Materials m JOIN SubCategory sc ON m.subCategoryId = sc.id WHERE m.status = true AND sc.categoryMaterialId = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }


    
    public Materials getMaterialsById(int id) {

        String sql = "SELECT * FROM Materials WHERE id = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                     m.setCreatedAt(rs.getTimestamp(COL_CREATED));
                    m.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
                    int rep2 = rs.getInt(COL_REPLACEMENT);
                    if(rep2 > 0) {
                        m.setReplacementMaterialId(getMaterialsById(rep2));
                    }
                    return m;
                }
            }

        } catch (Exception e) {
            Logger.getLogger(Materials.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    // Get new material that replaces the given material id
    public Materials getNewVersionOf(int oldId) {
        String sql = "SELECT * FROM Materials WHERE replacementMaterialId = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, oldId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    m.setCreatedAt(rs.getTimestamp(COL_CREATED));
                    m.setUpdatedAt(rs.getTimestamp(COL_UPDATED));
                    int rep = rs.getInt(COL_REPLACEMENT);
                    if (rep > 0) {
                        m.setReplacementMaterialId(getMaterialsById(rep));
                    }
                    return m;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    //create materials
    public int createMaterial(String name, int unitId, String image, int subCategoryId) {
    String sql = "INSERT INTO Materials(name, unitId, image, subCategoryId, status) VALUES(?,?,?,?, true)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, unitId);
            ps.setString(3, image);
            ps.setInt(4, subCategoryId);
            return ps.executeUpdate(); // >0 nếu thành công
        } catch (SQLException e) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    
    
    //update materials
    public int updateMaterial(int id, String name, int unitId, String imageName, int subCategoryId) {
StringBuilder sql = new StringBuilder("UPDATE Materials SET name=?, unitId=?, subCategoryId=?");
        if (imageName != null) {
            sql.append(", image=?");
        }
        sql.append(" WHERE id=?");
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            ps.setString(idx++, name);
            ps.setInt(idx++, unitId);
            ps.setInt(idx++, subCategoryId);
            if (imageName != null) {
                ps.setString(idx++, imageName);
            }
            ps.setInt(idx, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }    }

// Lấy danh sách vật tư đã bị xóa (statusId = 2)// Lấy danh sách vật tư đã bị xóa (status = false)
    public List<Materials> getDeletedMaterials() {
        List<Materials> list = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE status = false ORDER BY id DESC";
    try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
            }
public int getTotalDeletedMaterials() {
        String sql = "SELECT COUNT(*) FROM Materials WHERE status = false";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public List<Materials> pagingDeletedMaterials(int index) throws SQLException {
        List<Materials> list = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE status = false ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Materials m = new Materials();
                    m.setId(rs.getInt(COL_ID));
                    m.setName(rs.getString(COL_NAME));
                    m.setUnitId(mudao.getUnitById(rs.getInt(COL_UNIT)));
                    m.setSubCategoryId(scdao.getSubCategoryById(rs.getInt(COL_SUBCATEGORY)));
                    m.setImage(rs.getString(COL_IMAGE));
                    m.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(m);
                }
            }
        }
        return list;
    }
    // Khôi phục vật tư bằng cách chuyển trạng thái về hoạt động (statusId = true)
     public void activateMaterial(int id) {
        String sql = "UPDATE Materials SET status = true WHERE id = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //soft delete material
    public void deactivateMaterial(int deleteId) {
 String sql = "UPDATE Materials SET status = false WHERE id = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deleteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(MaterialsDAO.class.getName()).log(Level.SEVERE, null, e);
        }    }
    
    
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
