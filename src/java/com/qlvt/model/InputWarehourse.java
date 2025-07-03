/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.model;

/**
 *
 * @author D E L L
 */
public class InputWarehourse {
    private int id;
    private String dateInput;
    private User userId;

    public InputWarehourse() {
    }

    public InputWarehourse(int id, String dateInput, User userId) {
        this.id = id;
        this.dateInput = dateInput;
        this.userId = userId;
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
