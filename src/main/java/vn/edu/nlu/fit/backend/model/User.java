package vn.edu.nlu.fit.backend.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String createdAt;
    private String avatar;
    private String status;
    private String role;
    private String gender;
    private String dob;

    public User() {}

    public User(int id, String fullName, String email, String password, String phone,
                String createdAt, String avatar, String status, String role, String gender, String dob) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.createdAt = createdAt;
        this.avatar = avatar;
        this.status = status;
        this.role = role;
        this.gender = gender;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}