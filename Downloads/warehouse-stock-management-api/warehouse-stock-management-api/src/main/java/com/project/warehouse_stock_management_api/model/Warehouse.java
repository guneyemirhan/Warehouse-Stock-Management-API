package com.project.warehouse_stock_management_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok: getter, setter, toString gibi metotları otomatik oluşturur.
@Entity // JPA: Bu sınıfın bir veritabanı tablosu olduğunu belirtir.
@Table(name = "warehouses") // Tablonun adını "warehouses" olarak belirler.
public class Warehouse {

    @Id // Bu alanın birincil anahtar (primary key) olduğunu belirtir.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'lerin otomatik olarak artmasını sağlar (1, 2, 3...).
    private Long id;

    @Column(name = "name", nullable = false, unique = true) // "name" adında bir sütun oluşturur, boş olamaz ve benzersiz olmalıdır.
    private String name;

    @Column(name = "location") // "location" adında bir sütun oluşturur.
    private String location;
}