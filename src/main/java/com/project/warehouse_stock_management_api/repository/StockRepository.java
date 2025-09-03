package com.project.warehouse_stock_management_api.repository;

import com.project.warehouse_stock_management_api.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // YENİ IMPORT
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // Belirli bir depodaki belirli bir ürünün stok kaydını bulmak için.
    Optional<Stock> findByWarehouseIdAndProductId(Long warehouseId, Long productId);

    // --- YENİ EKLENEN METOT ---
    // Belirli bir depodaki tüm stok kayıtlarını bulur.
    List<Stock> findByWarehouseId(Long warehouseId);

}