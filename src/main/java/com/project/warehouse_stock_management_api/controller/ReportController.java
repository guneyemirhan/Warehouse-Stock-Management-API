package com.project.warehouse_stock_management_api.controller;

import com.project.warehouse_stock_management_api.dto.WarehouseReportDto;
import com.project.warehouse_stock_management_api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final StockService stockService;

    @Autowired
    public ReportController(StockService stockService) {
        this.stockService = stockService;
    }

    // Artık List<Stock> yerine WarehouseReportDto döndürüyor.
    @GetMapping("/stock-by-warehouse/{warehouseId}")
    public ResponseEntity<WarehouseReportDto> getStockByWarehouse(@PathVariable Long warehouseId) {
        WarehouseReportDto report = stockService.getStocksByWarehouse(warehouseId);
        return ResponseEntity.ok(report);
    }
}