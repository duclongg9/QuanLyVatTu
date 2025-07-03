/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.model;
import com.qlvt.model.Materials;
import com.qlvt.model.Supplier;

/**
 *
 * @author D E L L
 */
public class MaterialSupplier {
    private int id;
    private Materials materialId;
    private Supplier supplierId;
    private String note;
    private double price;

    public MaterialSupplier() {
    }

    public MaterialSupplier(int id, Materials materialId, Supplier supplierId, String note, double price) {
        this.id = id;
        this.materialId = materialId;
        this.supplierId = supplierId;
        this.note = note;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Materials getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Materials materialId) {
        this.materialId = materialId;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
}
