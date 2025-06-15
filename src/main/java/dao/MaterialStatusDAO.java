package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MaterialStatus;

public class MaterialStatusDAO {
    private final Connection conn;
    private static final String COL_ID = "id";
    private static final String COL_STATUS = "status";

    public MaterialStatusDAO() {
        conn = DBConnect.getConnection();
    }

    public MaterialStatus getStatusById(int id) {
        String sql = "SELECT * FROM materialstatus WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MaterialStatus m = new MaterialStatus();
                    m.setId(rs.getInt(COL_ID));
                    m.setStatus(rs.getString(COL_STATUS));
                    return m;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(MaterialStatusDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}