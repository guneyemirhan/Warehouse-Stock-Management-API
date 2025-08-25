package com.project.warehouse_stock_management_api.service;

import com.project.warehouse_stock_management_api.model.Warehouse;
import com.project.warehouse_stock_management_api.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Bu sınıfın bir "Service" katmanı bileşeni olduğunu Spring'e söyler.
public class WarehouseService {

    // Service katmanı, veritabanıyla konuşmak için Repository'ye ihtiyaç duyar.
    private final WarehouseRepository warehouseRepository;

    // Dependency Injection (Bağımlılık Enjeksiyonu):
    // Spring, bu sınıfı oluştururken ihtiyaç duyduğu WarehouseRepository'yi otomatik olarak bulur ve ona verir.
    // Bizim "new WarehouseRepository()" yapmamıza gerek kalmaz.
    @Autowired
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    // --- İŞ MANTIĞI METOTLARI ---

    // 1. Yeni bir depo oluşturan metot
    public Warehouse createWarehouse(Warehouse warehouse) {
        // İleride buraya "Aynı isimde başka bir depo var mı?" gibi kontroller eklenebilir.
        // Şimdilik, gelen depoyu doğrudan veritabanına kaydediyoruz.
        return warehouseRepository.save(warehouse);
    }

    // 2. Veritabanındaki tüm depoları listeleyen metot.
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    // 3. Verilen ID'ye göre tek bir depo getiren metot
    // "Optional", bir deponun bulunabileceği gibi bulunamayabileceği (null olabileceği) ihtimalini
    // daha güvenli bir şekilde yönetmemizi sağlar.
    public Optional<Warehouse> getWarehouseById(Long id) {
        return warehouseRepository.findById(id);
    }
}