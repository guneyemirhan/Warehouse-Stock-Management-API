package com.project.warehouse_stock_management_api.runner;

import com.project.warehouse_stock_management_api.model.Role;
import com.project.warehouse_stock_management_api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Uygulama başladığında bu metot otomatik olarak çalışır.

        // Veritabanında ROLE_USER var mı diye kontrol et, eğer yoksa oluştur.
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println(">>> ROLE_USER created successfully.");
        }

        // Veritabanında ROLE_ADMIN var mı diye kontrol et, eğer yoksa oluştur.
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            System.out.println(">>> ROLE_ADMIN created successfully.");
        }
    }
}