package com.project.warehouse_stock_management_api.repository;

import com.project.warehouse_stock_management_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Ürünleri benzersiz stok kodlarına (SKU) göre bulmak için
    // Spring Data JPA'nın sihirli metot isimlendirmesini kullanıyoruz.
    Optional<Product> findBySku(String sku);
}