package com.project.warehouse_stock_management_api.dto;

// Getter/Setter'ları manuel ekleyeceğiz
public class LoginRequest {
    private String username;
    private String password;

    // --- MANUEL GETTER VE SETTER'LAR ---
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
}