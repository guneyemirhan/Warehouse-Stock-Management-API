package com.project.warehouse_stock_management_api.dto;

public class StockRequest {
    private Long warehouseId;
    private Long productId;
    private Integer quantity;

    // Getter ve Setter'lar
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}