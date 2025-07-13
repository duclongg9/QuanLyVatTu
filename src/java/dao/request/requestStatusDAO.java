/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.request;

import dao.connect.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RequestStatus;

/**
 *
 * @author D E L L
 */
public class requestStatusDAO {

    private final Connection conn;

    private static final String COL_ID = "id";
    private static final String COL_STATUS = "status";

    public requestStatusDAO() {
        conn = DBConnect.getConnection();
    }
    
    
    //Lấy tất cả loại yêu cầu
    public List<String> getAllTypeOfRequest(){
        List<String> list = new ArrayList<>();
          String sql = """
                     SELECT DISTINCT type
                     FROM request; 
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String r = rs.getString("type");
                    list.add(r);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(RequestStatus.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }
    
    //Lấy tất cả trạng thái
    public List<RequestStatus> getStatusList() {
        List<RequestStatus> list = new ArrayList<>();
        String sql = """
                     SELECT * 
                     FROM requeststatus 
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    RequestStatus r = new RequestStatus();
                    r.setId(rs.getInt(COL_ID));
                    r.setStatus(rs.getString(COL_STATUS));
                    list.add(r);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(RequestStatus.class.getName()).log(Level.SEVERE, null, e);
        }

        return list;
    }

    //Lấy trạng thái yêu cầu theo id
    public RequestStatus getStatusById(int id) {

        String sql = """
                     SELECT * 
                     FROM requeststatus 
                     WHERE id =?
                    """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    RequestStatus r = new RequestStatus();
                    r.setId(rs.getInt(COL_ID));
                    r.setStatus(rs.getString(COL_STATUS));
                    return r;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(RequestStatus.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }
    
    public static void main(String[] args) {
        requestStatusDAO rsdao = new requestStatusDAO();
        List<RequestStatus> list = rsdao.getStatusList();
        for (RequestStatus requestStatus : list) {
            System.out.println(requestStatus);
        }
        
    }

}
