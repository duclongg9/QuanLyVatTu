/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.Category;

import dao.auditLog.AuditLogDAO;
import dao.connect.DBConnect;
import dao.user.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ActionType;
import model.Category;
import model.Materials;
import model.User;
import java.sql.Statement;

/**
 *
 * @author D E L L
 */
public class CategoryDAO {
//     private Connection conn;

    UserDAO udao = new UserDAO();
    CategoryDAO cdao = new CategoryDAO();

    public static final int PAGE_SIZE = 7;
    private static final String COL_ID = "id";
    private static final String COL_CATEGORY = "categoryName";
    private static final String COL_PARENT_CATE_ID = "parentCateId";
    private static final String COL_STATUS = "status";

    public CategoryDAO() {
//        conn = DBConnect.getConnection();
    }

    //phân trang cho phần danh mục con đã bị xóa
    public List<Category> pagingDeletedSubCategory(int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = false AND parentCateId != 0 ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setCategoryName(rs.getString("categoryName"));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean("status"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    //tạo danh mục con
    public int createSubCategory(String name, int categoryId,int changedBy) {
        String sql = "INSERT INTO Category (categoryName, parentCateId, status) VALUES (?, ?, true)";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            
            
            int newCateId = -1;

            ps.executeUpdate(); // Thực hiện insert

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newCateId = rs.getInt(1);
                }
            }

            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã thêm danh mục con mới:" + name;
            logDAO.insertAuditLog("Category", newCateId, ActionType.INSERT, message, changedBy);
            
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    //cập nhật danh mục con
    public int updateSubCategory(int id, String name, int categoryId,int changedBy) {
        String sql = "UPDATE Category SET categoryName = ?, parentCateId = ? WHERE id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            ps.setInt(3, id);
            
            User user = udao.getUserById(changedBy);
            Category category = cdao.getCategoryById(id);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã cập nhật lại danh mục con: " + name;
            logDAO.insertAuditLog("Category", id, ActionType.UPDATE, message, changedBy);
            
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    //đếm số lượng danh mục con theo tên
    public int getTotalSubCategoryByName(String name) {
        String sql = "SELECT COUNT(*) FROM Category WHERE status = true AND parentCateId != 0 AND categoryName LIKE ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    //tìm danh mục con theo tên(có phân trang)
    public List<Category> searchSubCategoryByName(String name, int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = true AND parentCateId != 0 AND categoryName LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setCategoryName(rs.getString("categoryName"));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean("status"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    //đếm số lượng danh mục cón
    public int getTotalSubCategory() {
        String sql = "SELECT COUNT(*) FROM Category WHERE status = true AND parentCateId != 0";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    //phân trang danh mục con
    public List<Category> pagingSubCategory(int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = true AND parentCateId != 0 ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setCategoryName(rs.getString("categoryName"));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean("status"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    //danh mục vật tư con trong danh mục cha, có phân trang
    public List<Category> searchSubCategoryByCategory(int categoryId, int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = true AND parentCateId = ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setCategoryName(rs.getString("categoryName"));
                    c.setParentCateId(rs.getInt("parentCateId"));
                    c.setStatus(rs.getBoolean("status"));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    //tắt danh mục con
    public int deleteSubCategory(int id,int changedBy) {
        String sql = "UPDATE Category SET status = false WHERE id = ? AND parentCateId != 0";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            User user = udao.getUserById(changedBy);
            Category category = cdao.getCategoryById(id);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã xóa danh mục con: " + category.getCategoryName();
            logDAO.insertAuditLog("Category", id, ActionType.DELETE, message, changedBy);
            
            return ps.executeUpdate(); // Trả về số dòng bị ảnh hưởng (1 nếu xóa thành công)
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // bật lại danh mục con
    public void activateSubCategory(int id, int changedBy) {
        String sql = "UPDATE Category SET status = true WHERE id = ? AND parentCateId != 0";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            User user = udao.getUserById(changedBy);
            Category category = cdao.getCategoryById(id);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã bật lại danh mục con: " + category.getCategoryName();
            logDAO.insertAuditLog("Category", id, ActionType.INSERT, message, changedBy);

            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //Lấy tổng số lượng SubCategory bị xóa(inactive)
    public int getTotalDeletedSubCategory() {
        String sql = "SELECT COUNT(*) FROM Category WHERE status = false AND parentCateId != 0";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Lấy tất cả danh mục vật tư con 
    public List<Category> getAllSubCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = true AND parentCateId != 0";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt(COL_ID));
                c.setCategoryName(rs.getString(COL_CATEGORY));
                c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                c.setStatus(rs.getBoolean(COL_STATUS));
                list.add(c);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    //-------
    public int deactivateByCategoryId(int categoryId,int changedBy) {
        String sql = "UPDATE Category SET status = false WHERE parentCateId = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            
            User user = udao.getUserById(changedBy);
            Category category = cdao.getCategoryById(categoryId);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã tắt danh mục: " + category.getCategoryName();
            logDAO.insertAuditLog("Category", categoryId, ActionType.INSERT, message, changedBy);
            
            return ps.executeUpdate(); // trả về số dòng bị ảnh hưởng
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    //Cây cha-con : cho việc làm menu
    public List<Category> getCategoryTree() {
        List<Category> flatList = getAllCategory();
        Map<Integer, Category> map = new HashMap<>();
        List<Category> roots = new ArrayList<>();

        for (Category c : flatList) {
            map.put(c.getId(), c);
        }

        for (Category c : flatList) {
            if (c.getParentCateId() == 0) {
                roots.add(c); // Cha gốc
            } else {
                Category parent = map.get(c.getParentCateId());
                if (parent != null) {
                    parent.addChild(c); // Gắn con vào cha
                }
            }
        }

        return roots; // Trả về danh sách danh mục gốc đã có con lồng trong
    }

    //tính tổng số lượng vật tư con theo vật tư cha
    public int getTotalSubCategoryByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Category WHERE status = true AND parentCateId = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    //Lấy danh mục vật tư con theo danh mục vật tư cha
    public List<Category> getSubCategoryByCatId(int categoryId) {
        List<Category> subCatList = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE 1=1 ";
        if (categoryId != 0) {
            sql += "AND parentCateId = ?";
        }
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            if (categoryId != 0) {
                ps.setInt(1, categoryId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    subCatList.add(c);
                }
                return subCatList;
            }

        } catch (Exception e) {
            Logger.getLogger(Materials.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    //lấy tất cả danh mục vật tư
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM ql_vat_tu.Category where status = true AND parentCateId IS NULL";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt(COL_ID));
                c.setCategoryName(rs.getString(COL_CATEGORY));
                c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                c.setStatus(rs.getBoolean(COL_STATUS));
                list.add(c);
            }
        } catch (Exception e) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    //Lấy category theo id 
    public Category getCategoryById(int id) {

        String sql = """
                     SELECT *
                    FROM Category
                    WHERE id = ? AND status = true
                    """;

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    return c;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }

    //lấy tất cả danh mục vật tư
    public List<Category> getCategoryList() throws SQLException {
        List<Category> cateMateList = new ArrayList<>();
        String sql = "SELECT * FROM ql_vat_tu.Category WHERE status = true";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    cateMateList.add(c);

                }
                return cateMateList;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // Tạo mới danh mục vật tư
    public int createCategory(String category,int changedBy) {
        String sql = "INSERT INTO Category(categoryName) VALUES(?)";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, category);
            int newCateId = -1;

            ps.executeUpdate(); // Thực hiện insert

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newCateId = rs.getInt(1);
                }
            }

            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã thêm danh mục cha mới:" + category;
            logDAO.insertAuditLog("Category", newCateId, ActionType.INSERT, message, changedBy);

            return newCateId;
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Cập nhật danh mục vật tư
    public int updateCategory(int id, String category,int changedBy) {
        String sql = "UPDATE Category SET categoryName=? WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            ps.setInt(2, id);
            
            User user = udao.getUserById(changedBy);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã cập nhật lại danh mục con: " + category;
            logDAO.insertAuditLog("Category", id, ActionType.UPDATE, message, changedBy);
            
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Delete category by id
    public int deleteCategory(int id,int changedBy) {
        String sql = "UPDATE Category SET status = false WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            User user = udao.getUserById(changedBy);
            Category category = cdao.getCategoryById(id);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã xóa danh mục cha: " + category.getCategoryName();
            logDAO.insertAuditLog("Category", id, ActionType.DELETE, message, changedBy);
            
            return ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Count categories
    public int getTotalCategories() {
        String sql = "SELECT COUNT(*) FROM Category WHERE status = true";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // Pagination for categories
    public List<Category> pagingCategories(int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = true ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Paging deleted categories
    public List<Category> pagingDeletedCategories(int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = false ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, PAGE_SIZE);
            ps.setInt(2, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Search categories by name with pagination
    public List<Category> searchCategoriesByName(String name, int index) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE status = true AND categoryName LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, PAGE_SIZE);
            ps.setInt(3, (index - 1) * PAGE_SIZE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt(COL_ID));
                    c.setCategoryName(rs.getString(COL_CATEGORY));
                    c.setParentCateId(rs.getInt(COL_PARENT_CATE_ID));
                    c.setStatus(rs.getBoolean(COL_STATUS));
                    list.add(c);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    // Count categories when searching by name
    public int getTotalCategoriesByName(String name) {
        String sql = "SELECT COUNT(*) FROM Category WHERE status = true AND categoryName LIKE ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    public int getTotalDeletedCategories() {
        String sql = "SELECT COUNT(*) FROM Category WHERE status = false";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }

    // restore category
    public void activateCategory(int id, int changedBy) {
        String sql = "UPDATE Category SET status = true WHERE id=?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

            User user = udao.getUserById(changedBy);
            Category category = cdao.getCategoryById(id);
            AuditLogDAO logDAO = new AuditLogDAO();
            String message = "User: " + user.getFullName() + " đã bật lại danh mục: " + category.getCategoryName();
            logDAO.insertAuditLog("Category", id, ActionType.INSERT, message, changedBy);

        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Check duplicate category name
    public boolean isNameExists(String name, Integer excludeId) {
        String sql = "SELECT COUNT(*) FROM Category WHERE LOWER(categoryName) = LOWER(?)";
        if (excludeId != null) {
            sql += " AND id <> ?";
        }
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            if (excludeId != null) {
                ps.setInt(2, excludeId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public static void main(String[] args) {
        CategoryDAO cdao = new CategoryDAO();
        List<Category> subCate = cdao.getAllCategory();
        for (Category category : subCate) {
            System.out.println(category);
        }
    }

}
