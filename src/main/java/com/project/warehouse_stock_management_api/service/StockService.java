package com.project.warehouse_stock_management_api.service;

import com.project.warehouse_stock_management_api.dto.StockRequest;
import com.project.warehouse_stock_management_api.dto.StockTransferRequest;
import com.project.warehouse_stock_management_api.model.Product;
import com.project.warehouse_stock_management_api.model.Stock;
import com.project.warehouse_stock_management_api.model.Warehouse;
import com.project.warehouse_stock_management_api.repository.ProductRepository;
import com.project.warehouse_stock_management_api.repository.StockRepository;
import com.project.warehouse_stock_management_api.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    @Autowired
    public StockService(StockRepository stockRepository, WarehouseRepository warehouseRepository, ProductRepository productRepository) {
        this.stockRepository = stockRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    // --- STOK GİRİŞİ İŞ MANTIĞI ---
    @Transactional
    public void addStock(StockRequest stockRequest) {
        Stock stock = stockRepository.findByWarehouseIdAndProductId(
                stockRequest.getWarehouseId(),
                stockRequest.getProductId()
        ).orElseGet(() -> {
            Warehouse warehouse = warehouseRepository.findById(stockRequest.getWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + stockRequest.getWarehouseId()));
            Product product = productRepository.findById(stockRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + stockRequest.getProductId()));

            Stock newStock = new Stock();
            newStock.setWarehouse(warehouse);
            newStock.setProduct(product);
            newStock.setQuantity(0);
            return newStock;
        });

        stock.setQuantity(stock.getQuantity() + stockRequest.getQuantity());
        stockRepository.save(stock);
    }

    // --- STOK TRANSFERİ İŞ MANTIĞI ---
    @Transactional
    public void transferStock(StockTransferRequest transferRequest) {
        if (transferRequest.getFromWarehouseId().equals(transferRequest.getToWarehouseId())) {
            throw new IllegalArgumentException("Source and destination warehouses cannot be the same.");
        }

        Stock fromStock = stockRepository.findByWarehouseIdAndProductId(transferRequest.getFromWarehouseId(), transferRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Stock not found in source warehouse"));

        if (fromStock.getQuantity() < transferRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock in source warehouse. Available: " + fromStock.getQuantity() + ", Requested: " + transferRequest.getQuantity());
        }

        Stock toStock = stockRepository.findByWarehouseIdAndProductId(transferRequest.getToWarehouseId(), transferRequest.getProductId())
                .orElseGet(() -> {
                    Warehouse toWarehouse = warehouseRepository.findById(transferRequest.getToWarehouseId())
                            .orElseThrow(() -> new RuntimeException("Destination warehouse not found with id: " + transferRequest.getToWarehouseId()));
                    Product product = fromStock.getProduct();

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
    }
}