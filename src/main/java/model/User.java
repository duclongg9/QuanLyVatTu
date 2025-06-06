<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author D E L L
 */
public class User {
    private int id;
    private String fullName;
    private boolean gender;
    private String birthDate;
    private String address;
    private String image;
    private String email;
    private String username;
    private String password;
    private String phone;
    private Role role;
    private boolean status;

    public User(int id, String fullName, boolean gender, String birthDate, String address, String image, String email, String username, String password, String phone, Role role, boolean status) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.image = image;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }
=======
package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role; // R1, R2, R3, R4
    private boolean active;
>>>>>>> origin/main

    public User() {
    }

<<<<<<< HEAD
=======
    public User(int id, String username, String password, String role, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

>>>>>>> origin/main
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

<<<<<<< HEAD
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

=======
>>>>>>> origin/main
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

<<<<<<< HEAD
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

   
    

    
    
    
=======
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
>>>>>>> origin/main
}
