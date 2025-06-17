/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import model.CategoryMaterial;
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
    private CategoryMaterial categoryId;
    private boolean status;
    public Materials() {
    }

    public Materials(int id, String name, MaterialUnit unitId, String image, CategoryMaterial categoryId, boolean status) {
        this.id = id;
        this.name = name;
        this.unitId = unitId;
        this.image = image;
        this.categoryId = categoryId;
        this.status = status;
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

    public CategoryMaterial getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryMaterial categoryId) {
        this.categoryId = categoryId;
    }
public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    
}
