package com.project.warehouse_stock_management_api.repository;

import com.project.warehouse_stock_management_api.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Bu interface'in bir Spring Bean'i olduğunu belirtir.
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {


}