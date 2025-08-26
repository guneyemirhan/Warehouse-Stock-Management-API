package com.project.warehouse_stock_management_api.service;

import com.project.warehouse_stock_management_api.dto.RegisterRequest;
import com.project.warehouse_stock_management_api.model.Role;
import com.project.warehouse_stock_management_api.model.User;
import com.project.warehouse_stock_management_api.repository.RoleRepository;
import com.project.warehouse_stock_management_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest registerRequest) {
        // Yeni bir kullanıcı nesnesi oluştur
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        // Şifreyi şifreleyerek kaydet
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Varsayılan olarak "ROLE_USER" rolünü ata
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        // Kullanıcıyı veritabanına kaydet
        return userRepository.save(user);
    }
}