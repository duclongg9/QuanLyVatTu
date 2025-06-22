package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dao.DBConnect;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.MaterialCostDetail;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ADMIN
 */
public class StaticsticsDAO {
    public Map<String, Double> getCostSummaryByQuarter(int year) {
        Map<String, Double> result = new LinkedHashMap<>();
        String sql = """
            SELECT QUARTER(r.date) AS quarter, r.type, 
                   SUM(idetail.quantity * idetail.inputPrice) AS total_cost
            FROM Request r
            JOIN requestDetail rd ON r.id = rd.requestId
            JOIN InputDetail idetail ON rd.id = idetail.requestDetailId
            WHERE YEAR(r.date) = ? AND r.type IN ('Import', 'Repair')
            GROUP BY QUARTER(r.date), r.type
            ORDER BY quarter;
        """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String key = "Q" + rs.getInt("quarter") + " - " + rs.getString("type");
                double cost = rs.getDouble("total_cost");
                result.put(key, cost);
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public List<MaterialCostDetail> getCostDetailsByQuarter(int year) {
        List<MaterialCostDetail> list = new ArrayList<>();
        String sql = """
            SELECT 
                m.name AS material_name,
                mu.unit AS unit_name,
                cm.category AS category_name,
                rd.quantity,
                idetail.inputPrice,
                (rd.quantity * idetail.inputPrice) AS total_cost,
                r.type,
                r.date,
                QUARTER(r.date) AS quarter
            FROM Request r
            JOIN requestDetail rd ON r.id = rd.requestId
            JOIN InputDetail idetail ON rd.id = idetail.requestDetailId
            JOIN Materials m ON rd.materialId = m.id
            JOIN MaterialUnit mu ON m.unitId = mu.id
            JOIN CategoryMaterial cm ON m.categoryId = cm.id
            WHERE YEAR(r.date) = ?
              AND r.type IN ('Import', 'Repair')
            ORDER BY quarter, r.date;
        """;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MaterialCostDetail detail = new MaterialCostDetail(
                    rs.getString("material_name"),
                    rs.getString("unit_name"),
                    rs.getString("category_name"),
                    rs.getInt("quantity"),
                    rs.getDouble("inputPrice"),
                    rs.getDouble("total_cost"),
                    rs.getString("type"),
                    rs.getDate("date"),
                    rs.getInt("quarter")
                );
                list.add(detail);
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
}
