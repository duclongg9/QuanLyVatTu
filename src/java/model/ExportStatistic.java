/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author KIET
 */
public class ExportStatistic {
    private int materialId;
    private String materialName;
    private String unitName;
    private int unitId;
    private double quantity;

   
    private String category;
    private String user;

    public ExportStatistic() {
    }

    public ExportStatistic(int materialId, String materialName, String unitName, int unitId, double quantity, String category, String user) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.unitName = unitName;
        this.unitId = unitId;
        this.quantity = quantity;
        this.category = category;
        this.user = user;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    

    

    
}
