/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class Category {
    private int id;
    private String categoryName;
    private int parentCateId;
    private boolean status;

    public Category() {
    }

    public Category(int id, String categoryName, int parentCateId, boolean status) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentCateId = parentCateId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getParentCateId() {
        return parentCateId;
    }

    public void setParentCateId(int parentCateId) {
        this.parentCateId = parentCateId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    
}