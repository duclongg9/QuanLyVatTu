/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qlvt.model;

import com.qlvt.model.Role;
import com.qlvt.model.Permission;


/**
 *
 * @author admin
 */
public class PermissionRole {
    private  int id;
    private Role role;
    private Permission permission;
    private boolean status;

    public PermissionRole() {
    }

    public PermissionRole(int id, Role role, Permission permission, boolean status) {
        this.id = id;
        this.role = role;
        this.permission = permission;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PermissionRole{" + "id=" + id 
                + ", role=" + role 
                + ", permission=" + permission 
                + ", status=" + status + '}';
    }
    
    
            
}
