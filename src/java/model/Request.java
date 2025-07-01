/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class Request {
    
    
    private int id;
    private String date;
    private RequestStatus statusId;
    private User userId;
    private String note;
    private RequestType type;
    private User approvedBy;

    public Request() {
    }

    public Request(int id, String date, RequestStatus statusId, User userId, String note, RequestType type, User approvedBy) {
        this.id = id;
        this.date = date;
        this.statusId = statusId;
        this.userId = userId;
        this.note = note;
        this.type = type;
        this.approvedBy = approvedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RequestStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(RequestStatus statusId) {
        this.statusId = statusId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvenBy) {
        this.approvedBy = approvenBy;
    }
    
    

   
    
    
    
}
