/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.unit;
import dao.connect.DBConnect;
import java.sql.Connection;
import java.util.List;
import model.Unit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author KIET
 */
public class UnitDAO {
    private Connection conn;

    public UnitDAO() {
        conn = DBConnect.getConnection();
    }

    public List<Unit> getAllUnits() {
    List<Unit> list = new ArrayList<>();
    String sql = "SELECT * FROM Unit";
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            list.add(new Unit(rs.getInt("id"), rs.getString("name"), rs.getBoolean("status")));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("Tổng số đơn vị tìm được: " + list.size());
    return list;
    
}

   public Unit getUnitById(int id) {
        String sql = "SELECT * FROM Unit WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Unit(rs.getInt("id"), rs.getString("name"), rs.getBoolean("status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void addOrRestoreUnit(String name) {
    String sqlCheck = "SELECT id, status FROM Unit WHERE name = ?";
    try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            boolean isActive = rs.getBoolean("status");
            int id = rs.getInt("id");

            if (!isActive) {
                // Tồn tại nhưng bị ẩn → khôi phục lại
                String restoreSql = "UPDATE Unit SET status = true WHERE id = ?";
                try (PreparedStatement psRestore = conn.prepareStatement(restoreSql)) {
                    psRestore.setInt(1, id);
                    psRestore.executeUpdate();
                }
            } else {
                // Tồn tại và đang hoạt động → báo lỗi
                throw new Exception("Tên đơn vị đã tồn tại!");
            }
        } else {
            // Thêm mới
            String insertSql = "INSERT INTO Unit(name, status) VALUES (?, true)";
            try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                psInsert.setString(1, name);
                psInsert.executeUpdate();
            }
        }
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage()); // Trả lỗi cho Servlet
    }
    }


    public void updateUnit(int id, String name) {
        String sql = "UPDATE Unit SET name = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    
    // NV1: Kiểm tra tên đơn vị đã tồn tại chưa
    public boolean isDuplicateName(String name) {
    String sql = "SELECT 1 FROM Unit WHERE name = ? AND status = true";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // Có tồn tại
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    
    // NV2: Kiểm tra xem đơn vị có đang được sử dụng trong bảng Materials không
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
    return true; // nếu lỗi, mặc định không cho xóa
}

    // NV3: Tìm kiếm đơn vị theo tên (có status = true)
public List<Unit> searchByName(String keyword) {
    List<Unit> list = new ArrayList<>();
    String sql = "SELECT * FROM Unit WHERE name LIKE ? AND status = true";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Unit(rs.getInt("id"), rs.getString("name"), rs.getBoolean("status")));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

// NV4: Lấy danh sách phân trang
public List<Unit> getUnitsByPage(int page, int pageSize) {
    List<Unit> list = new ArrayList<>();
    String sql = "SELECT * FROM Unit WHERE status = true LIMIT ? OFFSET ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, pageSize);
        ps.setInt(2, (page - 1) * pageSize);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Unit(rs.getInt("id"), rs.getString("name"), rs.getBoolean("status")));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

// NV5: Cập nhật trạng thái về false (ẩn đơn vị)
public void deactivateUnit(int id) {
    String sql = "UPDATE Unit SET status = false WHERE id = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Phụ trợ: Đếm tổng số đơn vị đang hoạt động
public int countUnits() {
    String sql = "SELECT COUNT(*) FROM Unit WHERE status = true";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
}


