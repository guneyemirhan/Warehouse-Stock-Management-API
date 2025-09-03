package com.project.warehouse_stock_management_api.service;

import com.project.warehouse_stock_management_api.dto.StockRequest;
import com.project.warehouse_stock_management_api.dto.StockTransferRequest;
import com.project.warehouse_stock_management_api.model.*;
import com.project.warehouse_stock_management_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final StockMovementLogRepository logRepository;
    private final UserRepository userRepository;

    @Autowired
    public StockService(StockRepository stockRepository, WarehouseRepository warehouseRepository, ProductRepository productRepository, StockMovementLogRepository logRepository, UserRepository userRepository) {
        this.stockRepository = stockRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    // --- STOK GİRİŞİ İŞ MANTIĞI ---
    @Transactional
    public void addStock(StockRequest stockRequest) {
        Warehouse warehouse = warehouseRepository.findById(stockRequest.getWarehouseId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + stockRequest.getWarehouseId()));
        Product product = productRepository.findById(stockRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + stockRequest.getProductId()));

        Stock stock = stockRepository.findByWarehouseIdAndProductId(warehouse.getId(), product.getId())
                .orElseGet(() -> {
                    Stock newStock = new Stock();
                    newStock.setWarehouse(warehouse);
                    newStock.setProduct(product);
                    newStock.setQuantity(0);
                    return newStock;
                });

        stock.setQuantity(stock.getQuantity() + stockRequest.getQuantity());
        stockRepository.save(stock);

        // Stok girişi için log oluştur ve kaydet
        createLog(null, warehouse, product, stockRequest.getQuantity(), "STOCK_ADDITION");
    }

    // --- STOK TRANSFERİ İŞ MANTIĞI ---
    @Transactional
    public void transferStock(StockTransferRequest transferRequest) {
        if (transferRequest.getFromWarehouseId().equals(transferRequest.getToWarehouseId())) {
            throw new IllegalArgumentException("Source and destination warehouses cannot be the same.");
        }

        Warehouse fromWarehouse = warehouseRepository.findById(transferRequest.getFromWarehouseId())
                .orElseThrow(() -> new RuntimeException("Source warehouse not found with id: " + transferRequest.getFromWarehouseId()));
        Warehouse toWarehouse = warehouseRepository.findById(transferRequest.getToWarehouseId())
                .orElseThrow(() -> new RuntimeException("Destination warehouse not found with id: " + transferRequest.getToWarehouseId()));
        Product product = productRepository.findById(transferRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + transferRequest.getProductId()));

        Stock fromStock = stockRepository.findByWarehouseIdAndProductId(fromWarehouse.getId(), product.getId())
                .orElseThrow(() -> new RuntimeException("Stock not found in source warehouse"));

        if (fromStock.getQuantity() < transferRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock in source warehouse. Available: " + fromStock.getQuantity() + ", Requested: " + transferRequest.getQuantity());
        }

        Stock toStock = stockRepository.findByWarehouseIdAndProductId(toWarehouse.getId(), product.getId())
                .orElseGet(() -> {
                    Stock newStock = new Stock();
                    newStock.setWarehouse(toWarehouse);
                    newStock.setProduct(product);
                    newStock.setQuantity(0);
                    return newStock;
                });

        fromStock.setQuantity(fromStock.getQuantity() - transferRequest.getQuantity());
        toStock.setQuantity(toStock.getQuantity() + transferRequest.getQuantity());

        stockRepository.save(fromStock);
        stockRepository.save(toStock);

        // Stok transferi için log oluştur ve kaydet
        createLog(fromWarehouse, toWarehouse, product, transferRequest.getQuantity(), "TRANSFER");
    }

    // Log oluşturmak için yardımcı metot
    private void createLog(Warehouse from, Warehouse to, Product product, Integer quantity, String type) {
        // İşlemi yapan mevcut kullanıcıyı al
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for logging"));

        StockMovementLog log = new StockMovementLog();
        log.setFromWarehouse(from);
        log.setToWarehouse(to);
        log.setProduct(product);
        log.setQuantity(quantity);
        log.setMovementType(type);
        log.setMovementDate(LocalDateTime.now());
    }
}