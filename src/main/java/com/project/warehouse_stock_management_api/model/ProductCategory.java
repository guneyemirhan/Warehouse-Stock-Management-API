package com.project.warehouse_stock_management_api.model;

import jakarta.persistence.*;
import java.util.Set;

// Lombok sorunları yaşamamak için getter/setter'ları yine manuel ekliyoruz.
@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // --- HİYERARŞİ İÇİN KRİTİK KISIM ---
    // Bir kategorinin bir üst kategorisi olabilir. (Çoktan Bire ilişki)
    // Örn: "Telefonlar" kategorisinin üst kategorisi "Elektronik"tir.
    @ManyToOne
    @JoinColumn(name = "parent_id") // Veritabanında "parent_id" adında bir sütun oluşturur.
    private ProductCategory parent;

    // Bir kategorinin birden fazla alt kategorisi olabilir. (Birden Çoğa ilişki)
    // "mappedBy = parent" -> Bu ilişkinin sahibinin diğer taraftaki "parent" alanı olduğunu belirtir.
    @OneToMany(mappedBy = "parent")
    private Set<ProductCategory> children;

    // --- MANUEL GETTER VE SETTER'LAR ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    public Set<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(Set<ProductCategory> children) {
        this.children = children;
    }




}