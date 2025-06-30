/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import model.SubCategory;
import model.MaterialUnit;

/**
 *
 * @author D E L L
 */
public class Materials {

    private int id;
    private String name;
    private MaterialUnit unitId;
    private String image;
    private SubCategory subCategoryId;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;
    private Materials replacementMaterialId;
    private boolean status;

    public Materials() {
    }

    public Materials(int id, String name, MaterialUnit unitId, String image, SubCategory subCategoryId, boolean status, java.sql.Timestamp createdAt, java.sql.Timestamp updatedAt, Materials replacementMaterialId) {        
        this.id = id;
        this.name = name;
        this.unitId = unitId;
        this.image = image;
        this.subCategoryId = subCategoryId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.replacementMaterialId = replacementMaterialId;
    }

    public Materials(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialUnit getUnitId() {
        return unitId;
    }

    public void setUnitId(MaterialUnit unitId) {
        this.unitId = unitId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

     public SubCategory getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(SubCategory subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public SubCategory getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(SubCategory subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
    
     public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public java.sql.Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Materials getReplacementMaterialId() {
        return replacementMaterialId;
    }

    public void setReplacementMaterialId(Materials replacementMaterialId) {
        this.replacementMaterialId = replacementMaterialId;
    }

    
}
