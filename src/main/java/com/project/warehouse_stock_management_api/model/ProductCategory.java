package com.project.warehouse_stock_management_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // YENİ EKLENEN IMPORT
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategory parent;

    // --- DEĞİŞİKLİK BURADA ---
    // JSON'a çevirirken bu alanı görmezden gelerek sonsuz döngüyü kırıyoruz.
    @OneToMany(mappedBy = "parent")
    @JsonIgnore
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

    public void setChildren(Set<ProductCategory> children){
        this.children = children;
    }
}