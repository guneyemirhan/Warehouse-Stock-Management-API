package com.project.warehouse_stock_management_api.repository;

import com.project.warehouse_stock_management_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Rolleri isimlerine göre bulmak için sihirli bir metot.
    Optional<Role> findByName(String name);
}