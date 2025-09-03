package com.project.warehouse_stock_management_api.dto;

import java.util.List;

public class WarehouseReportDto {
    private Long warehouseId;
    private String warehouseName;
    private List<ProductStockDto> stocks;

    // Getter ve Setter'lar
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }
    public List<ProductStockDto> getStocks() { return stocks; }
    public void setStocks(List<ProductStockDto> stocks) { this.stocks = stocks; }
}