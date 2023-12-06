package com.tienminh.a5s_project_hand_on.classes;

public class User {
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer is_admin;

    public User() {
        this.fullname = "";
        this.username = "";
        this.password = "";
        this.email = "";
        this.phone = "";
        this.is_admin = 0;
    }

    public User(String username, Integer is_admin) {
        this.username = username;
        this.is_admin = is_admin;
    }

    public User(String username, String password, Integer is_admin) {
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }

    public User(String fullname, String username, String password, String email, String phone, Integer is_admin) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.is_admin = is_admin;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Integer is_admin) {
        this.is_admin = is_admin;
    }
}
