package com.project.warehouse_stock_management_api.repository;

import com.project.warehouse_stock_management_api.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    // Şimdilik boş kalması yeterli. Spring Data JPA gerisini hallediyor.
}