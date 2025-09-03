package com.project.warehouse_stock_management_api.controller;

import com.project.warehouse_stock_management_api.dto.StockRequest;
import com.project.warehouse_stock_management_api.dto.StockTransferRequest;
import com.project.warehouse_stock_management_api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // --- STOK GİRİŞİ API'Sİ ---
    // Adres: POST /api/stocks/add
    @PostMapping("/add")
    public ResponseEntity<?> addStock(@RequestBody StockRequest stockRequest) {
        stockService.addStock(stockRequest);
        return ResponseEntity.ok("Stock added successfully.");
    }

    // --- DEPOLAR ARASI TRANSFER API'Sİ ---
    // Adres: POST /api/stocks/transfer
    @PostMapping("/transfer")
    public ResponseEntity<?> transferStock(@RequestBody StockTransferRequest transferRequest) {
        stockService.transferStock(transferRequest);
        return ResponseEntity.ok("Stock transferred successfully.");
    }
}