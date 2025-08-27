package com.project.warehouse_stock_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty") // Bu alanın boş olamayacağını ve sadece boşluktan oluşamayacağını belirtir.
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") // Uzunluk kısıtlaması ekler.
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 40, message = "Password must be between 6 and 40 characters")
    private String password;

    // Getter and Setter metotları aynı kalacak...
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}