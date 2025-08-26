package com.project.warehouse_stock_management_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku; // Stock Keeping Unit - Benzersiz Stok Kodu

    @Column(nullable = false)
    private String name;

    private String description;

    // Bir ürünün sadece bir kategorisi olabilir. (Çoktan Bire ilişki)
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Sadece ihtiyaç duyulduğunda kategoriyi yükle.
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    // --- MANUEL GETTER VE SETTER'LAR ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}