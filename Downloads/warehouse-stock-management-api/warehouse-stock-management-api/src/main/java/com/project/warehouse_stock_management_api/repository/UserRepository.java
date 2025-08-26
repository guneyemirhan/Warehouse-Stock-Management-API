package com.project.warehouse_stock_management_api.repository;

import com.project.warehouse_stock_management_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA'nın sihirli metotlarından biri:
    // Bu metodun ismine bakarak, "username" sütununda arama yapacak
    // bir SQL sorgusunu otomatik olarak oluşturur.
    // Kullanıcı adı ile bir kullanıcıyı bulmak için kullanacağız.
    Optional<User> findByUsername(String username);
}