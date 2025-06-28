/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author KIET
 */
public class UnitConversion {
    private int id;
    private int materialId;
    private int fromUnitId;
    private int toUnitId;
    private double ratio;
    

    private String materialName;
    private String fromUnitName;
    private String toUnitName;

    public UnitConversion() {
    }

    public UnitConversion(int materialId, int fromUnitId, int toUnitId, double ratio) {
        this.materialId = materialId;
        this.fromUnitId = fromUnitId;
        this.toUnitId = toUnitId;
        this.ratio = ratio;
    }
    
    

    public UnitConversion(int id, int materialId, int fromUnitId, int toUnitId, double ratio) {
        this.id = id;
        this.materialId = materialId;
        this.fromUnitId = fromUnitId;
        this.toUnitId = toUnitId;
        this.ratio = ratio;
    }

    
    public UnitConversion(int id, int materialId, int fromUnitId, int toUnitId, double ratio, String materialName, String fromUnitName, String toUnitName) {
        this.id = id;
        this.materialId = materialId;
        this.fromUnitId = fromUnitId;
        this.toUnitId = toUnitId;
        this.ratio = ratio;
        this.materialName = materialName;
        this.fromUnitName = fromUnitName;
        this.toUnitName = toUnitName;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getFromUnitId() {
        return fromUnitId;
    }

    public void setFromUnitId(int fromUnitId) {
        this.fromUnitId = fromUnitId;
    }

    public int getToUnitId() {
        return toUnitId;
    }

    public void setToUnitId(int toUnitId) {
        this.toUnitId = toUnitId;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getFromUnitName() {
        return fromUnitName;
    }

    public void setFromUnitName(String fromUnitName) {
        this.fromUnitName = fromUnitName;
    }

    public String getToUnitName() {
        return toUnitName;
    }

    public void setToUnitName(String toUnitName) {
        this.toUnitName = toUnitName;
    }
    
    
}
