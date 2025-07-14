/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.statistic;

import dao.connect.DBConnect;
import model.Statistic;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author KIET
 */
public class StatisticDAO {

    private Connection conn;

    public StatisticDAO() {
    }

    public StatisticDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Statistic> getExportStatistic(Date fromDate, Date toDate, int categoryId, int userId) {
        List<Statistic> list = new ArrayList<>();
        try {
                String sql = """
    SELECT 
        CONCAT(
            COALESCE(parent.categoryName, ''),
            CASE WHEN parent.categoryName IS NOT NULL THEN ' → ' ELSE '' END,
            c.categoryName
        ) AS category,
        u.fullname AS userFullName,
        m.name AS materialName,
        u2.unit AS unitName,
        SUM(od.quantity) AS totalExported
    FROM OutputDetail od
    JOIN OutputWarehouse ow ON od.outputWarehouseId = ow.id
    JOIN RequestDetail rd ON od.requestDetailId = rd.id
    JOIN MaterialItem mi ON rd.materialItemId = mi.id
    JOIN Materials_Supplier ms ON mi.materials_SupplierId = ms.id
    JOIN Materials m ON ms.materialId = m.id
    JOIN Category c ON m.category = c.id
    LEFT JOIN Category parent ON c.parentCateId = parent.id
    JOIN Request r ON rd.requestId = r.id
    JOIN User u ON r.userId = u.id
    JOIN MaterialUnit u2 ON m.unitId = u2.id
    WHERE ow.date BETWEEN ? AND ?
"""
+ (categoryId > 0 ? " AND c.id = ? " : "")
+ (userId > 0 ? " AND u.id = ? " : "")
+ """
    GROUP BY c.categoryName, parent.categoryName, u.fullname, m.name, u2.unit
    ORDER BY totalExported DESC
""";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, fromDate);
            ps.setDate(2, toDate);

            int paramIndex = 3;
            if (categoryId > 0) {
                ps.setInt(paramIndex++, categoryId);
            }
            if (userId > 0) {
                ps.setInt(paramIndex, userId);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Statistic ex = new Statistic();
                ex.setCategory(rs.getString("category"));
                ex.setUser(rs.getString("userFullName"));
                ex.setUnitName(rs.getString("unitName"));
                ex.setMaterialName(rs.getString("materialName"));
                ex.setQuantity(rs.getInt("totalExported"));
                list.add(ex);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Statistic> getImportStatistic(Date fromDate, Date toDate) {
        List<Statistic> list = new ArrayList<>();

        String sql = """
        SELECT m.id AS materialId, m.name AS materialName, u.unit AS unitName,
               SUM(idt.quantity) AS totalQuantity, m.unitId
        FROM InputDetail idt
        JOIN requestDetail rd ON idt.requestDetailId = rd.id
        JOIN MaterialItem mi ON rd.materialItemId = mi.id
        JOIN materials_Supplier ms ON mi.materials_SupplierId = ms.id
        JOIN Materials m ON ms.materialId = m.id
        JOIN MaterialUnit u ON m.unitId = u.id
        JOIN InputWarehouse iw ON idt.inputWarehouseId = iw.id
        WHERE iw.dateInput BETWEEN ? AND ?
        GROUP BY m.id, m.name, u.unit, m.unitId 
    """;
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, fromDate);
            ps.setDate(2, toDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Statistic s = new Statistic();
                s.setMaterialId(rs.getInt("materialId"));
                s.setMaterialName(rs.getString("materialName"));
                s.setUnitName(rs.getString("unitName"));
                s.setQuantity(rs.getDouble("totalQuantity"));
                s.setUnitId(rs.getInt("unitId"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Statistic> getImportByCategory(Date from, Date to) {
        List<Statistic> list = new ArrayList<>();
        String sql = """
        SELECT 
            CONCAT(
                COALESCE(parent.categoryName, ''),
                CASE WHEN parent.categoryName IS NOT NULL THEN ' → ' ELSE '' END,
                c.categoryName
            ) AS category,
            SUM(id.quantity) AS totalQuantity
        FROM InputDetail id
        JOIN requestDetail rd ON id.requestDetailId = rd.id
        JOIN MaterialItem mi ON rd.materialItemId = mi.id
        JOIN materials_Supplier ms ON mi.materials_SupplierId = ms.id
        JOIN Materials m ON ms.materialId = m.id
        JOIN Category c ON m.category = c.id
        LEFT JOIN Category parent ON c.parentCateId = parent.id
        JOIN InputWarehouse iw ON id.inputWarehouseId = iw.id
        WHERE iw.dateInput BETWEEN ? AND ?
        GROUP BY c.categoryName, parent.categoryName
    """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Statistic s = new Statistic();
                s.setCategory(rs.getString("category"));
                s.setQuantity(rs.getDouble("totalQuantity"));
                list.add(s);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Statistic> getImportByUser(Date from, Date to) {
        List<Statistic> list = new ArrayList<>();
        String sql = """
        SELECT u.fullName AS user, SUM(id.quantity) AS quantity
        FROM InputWarehouse iw
        JOIN User u ON iw.userId = u.id
        JOIN InputDetail id ON iw.id = id.inputWarehouseId
        WHERE iw.dateInput BETWEEN ? AND ?
        GROUP BY u.fullName
    """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Statistic s = new Statistic();
                    s.setUser(rs.getString("user"));
                    s.setQuantity(rs.getDouble("quantity"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Statistic> getImportByDate(Date from, Date to) {
        List<Statistic> list = new ArrayList<>();
        String sql = """
        SELECT iw.dateInput, SUM(id.quantity) AS totalQuantity
        FROM InputDetail id
        JOIN InputWarehouse iw ON id.inputWarehouseId = iw.id
        WHERE iw.dateInput BETWEEN ? AND ?
        GROUP BY iw.dateInput
        ORDER BY iw.dateInput 
    """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, from);
            ps.setDate(2, to);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Statistic s = new Statistic();
                // muon field `material name` đe truyen ngay dang chuoi
                s.setMaterialName(rs.getDate("dateInput").toString());
                s.setQuantity(rs.getDouble("totalQuantity"));
                list.add(s);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //cho dashbroad
    public int getTotalMaterials() {
    String sql = "SELECT COUNT(*) FROM Materials";
    return getSingleCount(sql);
}

public int getTotalCategories() {
    String sql = "SELECT COUNT(*) FROM Category WHERE status = 1"; 
    return getSingleCount(sql);
}

public int getTotalUnits() {
    String sql = "SELECT COUNT(*) FROM MaterialUnit"; 
    return getSingleCount(sql);
}

public int getTotalStatuses() {
    String sql = "SELECT COUNT(*) FROM MaterialStatus";
    return getSingleCount(sql);
}

    public Map<String, Integer> getMaterialCountByStatus() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT ms.status, SUM(mi.quantity) AS total "
                + "FROM MaterialItem mi "
                + "JOIN MaterialStatus ms ON mi.statusId = ms.id "
                + "GROUP BY ms.status";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("status"), rs.getInt("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> getMaterialCountByUnit() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql =  """
        SELECT mu.unit, COUNT(m.id) AS total
        FROM Materials m
        JOIN MaterialUnit mu ON m.unitId = mu.id
        GROUP BY mu.unit
    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("unit"), rs.getInt("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<Integer, Integer> getMonthlyInput() {
        Map<Integer, Integer> result = new TreeMap<>();
        String sql = """
        SELECT MONTH(iw.dateInput) AS month, SUM(id.quantity) AS total
        FROM InputWarehouse iw
        JOIN InputDetail id ON iw.id = id.inputWarehouseId
        GROUP BY MONTH(iw.dateInput)
        ORDER BY month
    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getInt("month"), rs.getInt("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<Integer, Integer> getMonthlyOutput() {
        Map<Integer, Integer> result = new TreeMap<>();
        String sql = """
        SELECT MONTH(ow.date) AS month, SUM(od.quantity) AS total
        FROM OutputWarehouse ow
        JOIN OutputDetail od ON ow.id = od.outputWarehouseId
        GROUP BY MONTH(ow.date)
        ORDER BY month
    """;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getInt("month"), rs.getInt("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getSingleCount(String sql) {
        int count = 0;
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Map<String, Object>> getRemainByCategoryAndStatus() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = """
        SELECT 
          CONCAT(COALESCE(parent.categoryName, ''), 
                 CASE WHEN parent.categoryName IS NOT NULL THEN ' → ' ELSE '' END, 
                 c.categoryName) AS category,
          ms.status AS status, 
          SUM(mi.quantity) AS total
        FROM MaterialItem mi
        JOIN materials_Supplier msup ON mi.materials_SupplierId = msup.id
        JOIN Materials m ON msup.materialId = m.id
        JOIN Category c ON m.category = c.id
        LEFT JOIN Category parent ON c.parentCateId = parent.id
        JOIN MaterialStatus ms ON mi.statusId = ms.id
        GROUP BY parent.categoryName, c.categoryName, ms.status
        ORDER BY parent.categoryName, c.categoryName, ms.status
    """;

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("category", rs.getString("category"));
                row.put("status", rs.getString("status"));
                row.put("total", rs.getInt("total"));
                result.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

