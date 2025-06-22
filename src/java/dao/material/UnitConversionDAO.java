/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.*;
import model.UnitConversion;
import dao.DBConnect;
/**
 *
 * @author KIET
 */
public class UnitConversionDAO {
    public List<UnitConversion> getAllUnitConversion() {
        List<UnitConversion> list = new ArrayList<>();
        String sql = "SELECT uc.*, m.name AS materialName, u1.name AS fromUnitName, u2.name AS toUnitName " +
                     "FROM UnitConversion uc " +
                     "JOIN Materials m ON uc.materialId = m.id " +
                     "JOIN Unit u1 ON uc.fromUnitId = u1.id " +
                     "JOIN Unit u2 ON uc.toUnitId = u2.id";
        try (Connection con = new DBConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                UnitConversion uc = new UnitConversion();
                uc.setId(rs.getInt("id"));
                uc.setMaterialId(rs.getInt("materialId"));
                uc.setFromUnitId(rs.getInt("fromUnitId"));
                uc.setToUnitId(rs.getInt("toUnitId"));
                uc.setRatio(rs.getDouble("ratio"));
                uc.setMaterialName(rs.getString("materialName"));
                uc.setFromUnitName(rs.getString("fromUnitName"));
                uc.setToUnitName(rs.getString("toUnitName"));
                list.add(uc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public UnitConversion getById(int id) {
        String sql = "SELECT * FROM UnitConversion WHERE id = ?";
        try (Connection con = new DBConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UnitConversion uc = new UnitConversion();
                uc.setId(rs.getInt("id"));
                uc.setMaterialId(rs.getInt("materialId"));
                uc.setFromUnitId(rs.getInt("fromUnitId"));
                uc.setToUnitId(rs.getInt("toUnitId"));
                uc.setRatio(rs.getDouble("ratio"));
                return uc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(UnitConversion uc) {
        String sql = "INSERT INTO UnitConversion (materialId, fromUnitId, toUnitId, ratio) VALUES (?, ?, ?, ?)";
        try (Connection con = new DBConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, uc.getMaterialId());
            ps.setInt(2, uc.getFromUnitId());
            ps.setInt(3, uc.getToUnitId());
            ps.setDouble(4, uc.getRatio());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(UnitConversion uc) {
        String sql = "UPDATE UnitConversion SET materialId = ?, fromUnitId = ?, toUnitId = ?, ratio = ? WHERE id = ?";
        try (Connection con = new DBConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, uc.getMaterialId());
            ps.setInt(2, uc.getFromUnitId());
            ps.setInt(3, uc.getToUnitId());
            ps.setDouble(4, uc.getRatio());
            ps.setInt(5, uc.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM UnitConversion WHERE id = ?";
        try (Connection con = new DBConnect().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
     // Trả về tỷ lệ quy đổi từ fromUnitId -> toUnitId
    public Double getConversionRate(int fromUnitId, int toUnitId, int materialId) {
        if (fromUnitId == toUnitId) return 1.0;
        String sql = "SELECT ratio FROM UnitConversion WHERE fromUnitId = ? AND toUnitId = ? AND materialId = ?";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fromUnitId);
            ps.setInt(2, toUnitId);
            ps.setInt(3, materialId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("ratio");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không có dữ liệu
    }

    // Quy đổi số lượng giữa các đơn vị
    public Double convertQuantity(int fromUnitId, int toUnitId, int materialId, double quantity) {
        Double rate = getConversionRate(fromUnitId, toUnitId, materialId);
        if (rate == null) return null;
        return quantity * rate;
    }

    // Nếu bạn muốn quy đổi luôn về "đơn vị chuẩn" của 1 vật tư (ví dụ tờ, cái, ...)
    // thì cần định nghĩa baseUnitId trong bảng Materials (chưa có hiện tại)
    // bạn có thể mở rộng sau nếu cần.
}


