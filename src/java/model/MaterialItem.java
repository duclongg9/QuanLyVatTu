/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class MaterialItem {
    private int id;
    private MaterialStatus statusId;
    private int quantity;
    private MaterialSupplier materialSupplier;
    
    private int selectedQuantity = 0;// Đây là một trường ảo, Áp dụng kỹ thuật làm giàu dữ liệu(mục đích ghi nhớ lại số lượng người dùng đã chọn(mà chưa submit) để đẩy tiếp lại lên session hỗ trợ người dùng)
    
    public MaterialItem() {
    }

    public MaterialItem(int id, MaterialStatus statusId, int quantity, MaterialSupplier materialSupplier) {
        this.id = id;
        this.statusId = statusId;
        this.quantity = quantity;
        this.materialSupplier = materialSupplier;
    }

    public MaterialStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(MaterialStatus statusId) {
        this.statusId = statusId;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }
    
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MaterialStatus getStarusId() {
        return statusId;
    }

    public void setStarusId(MaterialStatus statusId) {
        this.statusId = statusId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MaterialSupplier getMaterialSupplier() {
        return materialSupplier;
    }

    public void setMaterialSupplier(MaterialSupplier materialSupplier) {
        this.materialSupplier = materialSupplier;
    }

    public void setMaterialsSupplierId(MaterialSupplier byId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
