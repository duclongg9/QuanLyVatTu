/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class OutputWarehourse {

    private int id;
    private String date;
    private User userId;
    private String reason;
    private String note;
    private Request request;
    private RequestType type;

    public OutputWarehourse() {
    }

    public OutputWarehourse(int id, String date, User userId, String reason, String note, Request request, RequestType type) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.reason = reason;
        this.note = note;
        this.request = request;
        this.type = type;
    }

    
    
    public OutputWarehourse(int id, String date, User userId, RequestType type) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.type = type;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    
    

}
