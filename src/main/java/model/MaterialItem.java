package model;

public class MaterialItem {
    private int id;
    private Materials materialId;
    private MaterialStatus statusId;
    private int quantity;

    public MaterialItem() {
    }

    public MaterialItem(int id, Materials materialId, MaterialStatus statusId, int quantity) {
        this.id = id;
        this.materialId = materialId;
        this.statusId = statusId;
        this.quantity = quantity;
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
}