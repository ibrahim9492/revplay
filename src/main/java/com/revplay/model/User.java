package com.revplay.model;

public class User {

    private int id;
    private String email;
    private String role;

    // Constructor with ID (used when ID is known)
    public User(int id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    // Constructor without ID (used during login/register)
    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
