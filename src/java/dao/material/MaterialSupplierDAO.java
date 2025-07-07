/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.material;

import com.oracle.wls.shaded.org.apache.bcel.generic.I2B;
import dao.Supplier.SupplierDAO;
import dao.connect.DBConnect;
import dao.role.RoleDAO;
import dao.user.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MaterialSupplier;
import model.Supplier;
import model.User;

/**
 *
 * @author D E L L
 */
public class MaterialSupplierDAO {

    private static final String COL_ID = "id";
    private static final String COL_MATERIALID = "materialId";
    private static final String COL_SUPPLIERID = "supplierId";
    private static final String COL_NOTE = "note";
    private static final String COL_PRICE = "price";

    private static final int PAGE_SIZE = 5;

    private Connection conn;

    RoleDAO rdao = new RoleDAO();
    MaterialsDAO mdao = new MaterialsDAO();
    SupplierDAO sdao = new SupplierDAO();

    public MaterialSupplierDAO() {
        conn = DBConnect.getConnection();
    }

    public List<Supplier> getListSupplierByMaterialId(int materialId) {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT supplierId FROM materials_Supplier WHERE materialId = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, materialId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int supplierId = rs.getInt("supplierId");

                    // Gọi hàm có sẵn để lấy Supplier
                    Supplier supplier = sdao.getSupplierById(supplierId);
                    if (supplier != null) {
                        list.add(supplier);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public MaterialSupplier getMaterialSupplierById(int materialSupplierId) {
        String sql = """
                     SELECT * 
                     FROM materials_Supplier 
                     WHERE id =?
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, materialSupplierId);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    MaterialSupplier p = new MaterialSupplier();
                    p.setId(rs.getInt(COL_ID));
                    p.setMaterialId(mdao.getMaterialsById(rs.getInt(COL_MATERIALID)));
                    p.setSupplierId(sdao.getSupplierById(rs.getInt(COL_SUPPLIERID)));
                    p.setNote(rs.getString(COL_NOTE));
                    return p;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialSupplier.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    //Phân trang
    public List<MaterialSupplier> pagingMaterialWithSupplier(int index) throws SQLException {
        List<MaterialSupplier> list = new ArrayList<>();
        String sql = "SELECT * FROM materials_supplier\n"
                + "LIMIT ? OFFSET ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    MaterialSupplier p = new MaterialSupplier();
                    p.setId(rs.getInt(COL_ID));
                    p.setMaterialId(mdao.getMaterialsById(rs.getInt(COL_MATERIALID)));
                    p.setSupplierId(sdao.getSupplierById(rs.getInt(COL_SUPPLIERID)));
                    p.setNote(rs.getString(COL_NOTE));
                    p.setPrice(rs.getDouble(COL_PRICE));
                    list.add(p);
                }
            } catch (Exception e) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
            }

            return list;
        }
    }

    //đếm số lượng người dùng trong database
    public int getTotalMaterialWithSupplier() {
        String sql = "SELECT COUNT(*) FROM materials_supplier;";
        try (PreparedStatement ps = conn.prepareStatement(sql);) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return 0;
    }

    public MaterialSupplier getById(int id) {
        String sql = "SELECT * FROM materials_supplier WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MaterialSupplier p = new MaterialSupplier();
                    p.setId(rs.getInt(COL_ID));
                    p.setMaterialId(mdao.getMaterialsById(rs.getInt(COL_MATERIALID)));
                    p.setSupplierId(sdao.getSupplierById(rs.getInt(COL_SUPPLIERID)));
                    p.setNote(rs.getString(COL_NOTE));
                    p.setPrice(rs.getDouble(COL_PRICE));
                    return p;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialSupplierDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
