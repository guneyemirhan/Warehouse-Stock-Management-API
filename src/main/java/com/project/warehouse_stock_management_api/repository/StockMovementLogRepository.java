package com.project.warehouse_stock_management_api.repository;

import com.project.warehouse_stock_management_api.model.StockMovementLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementLogRepository extends JpaRepository<StockMovementLog, Long> {

}