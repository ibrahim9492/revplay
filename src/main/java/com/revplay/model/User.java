package com.revplay.model;

public class User {

    private int id;
    private String email;
    private String role;

    public User(int id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

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

    public boolean isArtist() {
        return "ARTIST".equalsIgnoreCase(role);
    }
}
