/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dell-PC
 */
public class MaterialItem {
    private int id;
    private MaterialStatus statusId;
    private int quantity;
    private MaterialSupplier materialsSupplierId;

    public MaterialItem() {
    }

    public MaterialItem(int id, MaterialStatus statusId, int quantity, MaterialSupplier materialsSupplierId) {
        this.id = id;
        this.statusId = statusId;
        this.quantity = quantity;
        this.materialsSupplierId = materialsSupplierId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MaterialStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(MaterialStatus statusId) {
        this.statusId = statusId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MaterialSupplier getMaterialsSupplierId() {
        return materialsSupplierId;
    }

    public void setMaterialsSupplierId(MaterialSupplier materialsSupplierId) {
        this.materialsSupplierId = materialsSupplierId;
    }
}
