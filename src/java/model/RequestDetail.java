/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import model.Request;
import model.Materials;
/**
 *
 * @author D E L L
 */
public class RequestDetail {
    private int id;
    private Request requestId;
    private Materials materialId;
    private Supplier supplierId;
    private int quantity;
    private String note;

    public RequestDetail() {
    }

    public RequestDetail(int id, Request requestId, Materials materialId, Supplier supplierId, int quantity, String note) {
        this.id = id;
        this.requestId = requestId;
        this.materialId = materialId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.note = note;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Request getRequestId() {
        return requestId;
    }

    public void setRequestId(Request requestId) {
        this.requestId = requestId;
    }

    public Materials getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Materials materialId) {
        this.materialId = materialId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }
    
    
    
}
