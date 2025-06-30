/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class InputDetail {
    private int id;
    private int quantity;
    private InputWarehourse inputWarehourseId;
    private RequestDetail requestDetailId;
    private MaterialItem materialItemId;
    private double inputPrice;

    public InputDetail() {
    }

    public InputDetail(int id, int quantity, InputWarehourse inputWarehourseId, RequestDetail requestDetailId, MaterialItem materialItemId, double inputPrice) {
        this.id = id;
        this.quantity = quantity;
        this.inputWarehourseId = inputWarehourseId;
        this.requestDetailId = requestDetailId;
        this.materialItemId = materialItemId;
        this.inputPrice = inputPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public InputWarehourse getInputWarehourseId() {
        return inputWarehourseId;
    }

    public void setInputWarehourseId(InputWarehourse inputWarehourseId) {
        this.inputWarehourseId = inputWarehourseId;
    }

    public RequestDetail getRequestDetailId() {
        return requestDetailId;
    }

    public void setRequestDetailId(RequestDetail requestDetailId) {
        this.requestDetailId = requestDetailId;
    }

    public MaterialItem getMaterialItemId() {
        return materialItemId;
    }

    public void setMaterialItemId(MaterialItem materialItemId) {
        this.materialItemId = materialItemId;
    }

    public double getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(double inputPrice) {
        this.inputPrice = inputPrice;
    }
    
    
}
