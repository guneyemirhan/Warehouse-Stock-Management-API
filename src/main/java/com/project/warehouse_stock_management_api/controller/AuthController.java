package com.project.warehouse_stock_management_api.controller;

import com.project.warehouse_stock_management_api.dto.AuthResponse;
import com.project.warehouse_stock_management_api.dto.LoginRequest;
import com.project.warehouse_stock_management_api.dto.RegisterRequest;
import com.project.warehouse_stock_management_api.security.JwtUtil; // YENİ
import com.project.warehouse_stock_management_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager; // YENİ
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // YENİ
import org.springframework.security.core.Authentication; // YENİ
import org.springframework.security.core.userdetails.UserDetails; // YENİ
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager; // YENİ
    private final JwtUtil jwtUtil; // YENİ

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully!");
    }

    // --- YENİ EKLENEN LOGIN METODU ---
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // 1. Spring Security'e kullanıcı adı ve şifreyi doğrulaması için gönder.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        // 2. Eğer doğrulama başarılıysa, kullanıcı detaylarını al.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Kullanıcı için bir JWT token oluştur.
        String jwt = jwtUtil.generateToken(userDetails);

        // 4. Token'ı cevap olarak geri dön.
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}