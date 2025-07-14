/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

public class MaterialHistory {
    private int id;
    private Materials material;
    private String name;
    private MaterialUnit unit;
    private String image;
    private Category subCategory;
    private java.sql.Timestamp archivedAt;

    public MaterialHistory() {
    }

    public MaterialHistory(int id, Materials material, String name, MaterialUnit unit, String image, Category subCategory, java.sql.Timestamp archivedAt) {
        this.id = id;
        this.material = material;
        this.name = name;
        this.unit = unit;
        this.image = image;
        this.subCategory = subCategory;
        this.archivedAt = archivedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Materials getMaterial() {
        return material;
    }

    public void setMaterial(Materials material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MaterialUnit getUnit() {
        return unit;
    }

    public void setUnit(MaterialUnit unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Category subCategory) {
        this.subCategory = subCategory;
    }

    public Timestamp getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Timestamp archivedAt) {
        this.archivedAt = archivedAt;
    }

    
}