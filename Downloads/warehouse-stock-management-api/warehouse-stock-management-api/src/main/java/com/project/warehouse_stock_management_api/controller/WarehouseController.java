package com.project.warehouse_stock_management_api.controller;

import com.project.warehouse_stock_management_api.model.Warehouse;
import com.project.warehouse_stock_management_api.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bu sınıfın bir REST API Controller'ı olduğunu belirtir.
@RequestMapping("/api/warehouses") // burada ki controller'daki tüm API'lerin "/api/warehouses" adresiyle başlayacağını söyler.
public class WarehouseController {

    private final WarehouseService warehouseService;

    // Controller, iş mantığını yaptırmak için Service'e ihtiyaç duyar.
    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    // --- API ENDPOINT'LERİ ---

    // 1. YENİ BİR DEPO OLUŞTURMA API'Sİ
    // HTTP POST isteği ile çalışır. Adresi: POST /api/warehouses
    @PostMapping
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse newWarehouse = warehouseService.createWarehouse(warehouse);
        return new ResponseEntity<>(newWarehouse, HttpStatus.CREATED); // 201 Created durum koduyla yanıt döner.
    }

    // 2. TÜM DEPOLARI LİSTELEME API'Sİ
    // HTTP GET isteği ile çalışır. Adresi: GET /api/warehouses
    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseService.getAllWarehouses();
        return new ResponseEntity<>(warehouses, HttpStatus.OK); // 200 OK durum koduyla yanıt döner.
    }

    // 3. ID'YE GÖRE TEK BİR DEPO GETİRME API'Sİ
    // HTTP GET isteği ile çalışır. Adresi: GET /api/warehouses/{id} (Örn: /api/warehouses/1)
    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable Long id) {
        return warehouseService.getWarehouseById(id)
                .map(warehouse -> new ResponseEntity<>(warehouse, HttpStatus.OK)) // Depo bulunursa 200 OK döner.
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Depo bulunamazsa 404 Not Found döner.
    }
}