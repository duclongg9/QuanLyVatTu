/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class AuditLog {
    private int id;
    private String tableName;
    private int recordId;
    private ActionType actionType;
    private String message; 
    private User changeBy;
    private String changeAt;

    public AuditLog() {
    }

    public AuditLog(int id, String tableName, int recordId, ActionType actionType, String message, User changeBy, String changeAt) {
        this.id = id;
        this.tableName = tableName;
        this.recordId = recordId;
        this.actionType = actionType;
        this.message = message;
        this.changeBy = changeBy;
        this.changeAt = changeAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getChangeBy() {
        return changeBy;
    }

    public void setChangeBy(User changeBy) {
        this.changeBy = changeBy;
    }

    public String getChangeAt() {
        return changeAt;
    }

    public void setChangeAt(String changeAt) {
        this.changeAt = changeAt;
    }

   

    
}
