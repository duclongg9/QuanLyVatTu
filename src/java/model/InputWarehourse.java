/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class InputWarehourse {
    private int id;
    private String dateInput;
    private User userId;
    private String reason;
    private String note;
    private Request request;

    public InputWarehourse() {
    }

    public InputWarehourse(int id, String dateInput, User userId, String reason, String note, Request request) {
        this.id = id;
        this.dateInput = dateInput;
        this.userId = userId;
        this.reason = reason;
        this.note = note;
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request requestId) {
        this.request = requestId;
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

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateInput() {
        return dateInput;
    }

    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
    
    
}
