package com.project.warehouse_stock_management_api.dto;

public class ProductStockDto {
    private Long productId;
    private String productSku;
    private String productName;
    private Integer quantity;

    // Getter ve Setter'lar
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductSku() { return productSku; }
    public void setProductSku(String productSku) { this.productSku = productSku; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}