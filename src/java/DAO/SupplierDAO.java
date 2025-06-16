package DAO;

import Model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO extends DBConnect {

    // Thêm nhà cung cấp mới (status mặc định là true = đang hoạt động)
    public boolean addSupplier(String name, String phone, String address) {
        String sql = "INSERT INTO `Supplier` (`name`, `phone`, `address`, `status`) VALUES (?, ?, ?, ?)";
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setBoolean(4, true); // Trạng thái hoạt động
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public boolean isPhoneExists(String phone) {
        String sql = "SELECT COUNT(*) FROM Supplier WHERE phone = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPhoneExistsForOtherSupplier(String phone, int currentSupplierId) {
        String sql = "SELECT COUNT(*) FROM Supplier WHERE phone = ? AND id <> ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ps.setInt(2, currentSupplierId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách tất cả nhà cung cấp
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM `Supplier`";
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setAddress(rs.getString("address"));
                supplier.setStatus(rs.getBoolean("status"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return suppliers;
    }

    // Lấy thông tin nhà cung cấp theo ID
    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM `Supplier` WHERE `id` = ?";
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setPhone(rs.getString("phone"));
                supplier.setAddress(rs.getString("address"));
                supplier.setStatus(rs.getBoolean("status"));
                return supplier;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy nhà cung cấp theo ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    // Cập nhật thông tin nhà cung cấp
    public void updateSupplier(Supplier supplier) {
        String sql = "UPDATE Supplier SET name = ?, phone = ?, address = ?, status = ? WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getPhone());
            ps.setString(3, supplier.getAddress());
            ps.setBoolean(4, supplier.isStatus());
            ps.setInt(5, supplier.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa cứng nhà cung cấp (xóa khỏi database)
    public boolean deleteSupplier(int id) {
        String sql = "DELETE FROM `Supplier` WHERE `id` = ?";
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    // Xóa mềm nhà cung cấp (chỉ đổi status = false)
    public boolean softDeleteSupplier(int id) {
        String sql = "UPDATE `Supplier` SET `status` = 0 WHERE `id` = ?";
        Connection conn = getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa mềm nhà cung cấp: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public List<Supplier> getSuppliersByPage(int offset, int limit) {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM Supplier ORDER BY id LIMIT ? OFFSET ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("status")
                );
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSuppliers() {
        String sql = "SELECT COUNT(*) FROM Supplier";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Supplier> searchByNamePaging(String keyword, int offset, int limit) {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM Supplier WHERE name LIKE ? LIMIT ?, ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, offset);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSearchByName(String keyword) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Supplier WHERE name LIKE ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Supplier> searchSuppliersByNameAndStatus(String keyword, String statusFilter, int offset, int limit) {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM Supplier WHERE name LIKE ?";

        if (!statusFilter.equals("all")) {
            sql += " AND status = ?";
        }

        sql += " LIMIT ?, ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            int index = 2;
            if (!statusFilter.equals("all")) {
                ps.setBoolean(index++, statusFilter.equals("active"));
            }

            ps.setInt(index++, offset);
            ps.setInt(index, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBoolean("status")
                );
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countSuppliersByNameAndStatus(String keyword, String statusFilter) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Supplier WHERE name LIKE ?";

        if (!statusFilter.equals("all")) {
            sql += " AND status = ?";
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            if (!statusFilter.equals("all")) {
                ps.setBoolean(2, statusFilter.equals("active"));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Hàm kiểm tra (test) danh sách nhà cung cấp
    public static void main(String[] args) {
        SupplierDAO dao = new SupplierDAO();
        List<Supplier> list = dao.getAllSuppliers();
        for (Supplier s : list) {
            System.out.println(s);
        }
    }
}
