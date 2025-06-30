/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class OutputDetail {
    private int id;
    private int quantity;
    private OutputWarehourse outputWarehourseId;
    private RequestDetail requestDetailId;
    private MaterialItem materialItemId;

    public OutputDetail() {
    }

    public OutputDetail(int id, int quantity, OutputWarehourse outputWarehourseId, RequestDetail requestDetailId, MaterialItem materialItemId) {
        this.id = id;
        this.quantity = quantity;
        this.outputWarehourseId = outputWarehourseId;
        this.requestDetailId = requestDetailId;
        this.materialItemId = materialItemId;
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

    public OutputWarehourse getOutputWarehourseId() {
        return outputWarehourseId;
    }

    public void setOutputWarehourseId(OutputWarehourse outputWarehourseId) {
        this.outputWarehourseId = outputWarehourseId;
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
    
    
}
