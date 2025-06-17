/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import model.Materials;
import model.Supplier;

/**
 *
 * @author D E L L
 */
public class MaterialSupplier {
    private int id;
    private Materials materialId;
    private Supplier supplierId;
    private String note;

    public MaterialSupplier() {
    }

    public MaterialSupplier(int id, Materials materialId, Supplier supplierId, String note) {
        this.id = id;
        this.materialId = materialId;
        this.supplierId = supplierId;
        this.note = note;
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
