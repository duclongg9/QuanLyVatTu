/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.unit;

import dao.auditLog.AuditLogDAO;
import dao.connect.DBConnect;
import dao.material.MaterialUnitDAO;
import dao.user.UserDAO;
import java.sql.Connection;
import java.util.List;
import model.Unit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.ActionType;
import model.User;
import java.sql.Statement;
import model.MaterialUnit;

/**
 *
 * @author KIET
 */
public class UnitDAO {

    private Connection conn;
    UserDAO udao = new UserDAO();
    MaterialUnitDAO mudao = new MaterialUnitDAO();

    public UnitDAO() {
        conn = DBConnect.getConnection();
    }

    public List<Unit> getAllUnits() {
        List<Unit> list = new ArrayList<>();
        String sql = "SELECT * FROM MaterialUnit";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Unit(rs.getInt("id"), rs.getString("unit")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Unit getUnitById(int id) {
        String sql = "SELECT * FROM MaterialUnit WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Unit(rs.getInt("id"), rs.getString("unit"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUnit(String unit, int changedBy) {
        String sql = "INSERT INTO MaterialUnit(unit) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, unit);

            int newUnitId = -1;
            ps.executeUpdate(); // Thực hiện insert trước

            // Lấy ID tự sinh
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newUnitId = rs.getInt(1);
                }
            }

            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã thêm đơn vị tính mới:" + unit;
            logDAO.insertAuditLog("Unit", newUnitId, ActionType.INSERT, message, changedBy);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateUnit(int id, String unit, int changedBy) {
        String sql = "UPDATE MaterialUnit SET unit = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, unit);
            ps.setInt(2, id);

            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã cập nhật lại đơn vị tính :" + unit;
            logDAO.insertAuditLog("Unit", id, ActionType.UPDATE, message, changedBy);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUnit(int id,int changedBy) {
        String sql = "DELETE FROM MaterialUnit WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            User user = udao.getUserById(changedBy);
            MaterialUnit unit = mudao.getUnitById(id);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName()+" đã tắt đơn vị tính: "+unit.getUnitName();
            logDAO.insertAuditLog("Unit", id, ActionType.DELETE, message, changedBy);
            
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDuplicateUnit(String unit) {
        String sql = "SELECT 1 FROM MaterialUnit WHERE unit = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, unit);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsedInMaterials(int unitId) {
        String sql = "SELECT COUNT(*) FROM Materials WHERE unitId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, unitId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<Unit> searchByUnit(String keyword) {
        List<Unit> list = new ArrayList<>();
        String sql = "SELECT * FROM MaterialUnit WHERE unit LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Unit(rs.getInt("id"), rs.getString("unit")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Unit> getUnitsByPage(int page, int pageSize) {
        List<Unit> list = new ArrayList<>();
        String sql = "SELECT * FROM MaterialUnit LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Unit(rs.getInt("id"), rs.getString("unit")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countUnits() {
        String sql = "SELECT COUNT(*) FROM MaterialUnit";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
