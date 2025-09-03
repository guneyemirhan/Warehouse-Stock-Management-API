package com.project.warehouse_stock_management_api.service;

import com.project.warehouse_stock_management_api.dto.StockTransferRequest;
import com.project.warehouse_stock_management_api.model.Product;
import com.project.warehouse_stock_management_api.model.Stock;
import com.project.warehouse_stock_management_api.model.User;
import com.project.warehouse_stock_management_api.model.Warehouse;
import com.project.warehouse_stock_management_api.repository.ProductRepository;
import com.project.warehouse_stock_management_api.repository.StockRepository;
import com.project.warehouse_stock_management_api.repository.UserRepository;
import com.project.warehouse_stock_management_api.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;
    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository; // Gerekli UserRepository mock'u eklendi.

    // Not: LogRepository'yi de mock'lamamız gerekiyor, çünkü createLog metodu onu kullanıyor.
    @Mock
    private com.project.warehouse_stock_management_api.repository.StockMovementLogRepository logRepository;

    @InjectMocks
    private StockService stockService;

    private Warehouse warehouseFrom;
    private Warehouse warehouseTo;
    private Product product;
    private Stock fromStock;
    private Stock toStock;
    private StockTransferRequest transferRequest;

    @BeforeEach
    void setUp() {
        warehouseFrom = new Warehouse();
        warehouseFrom.setId(1L);
        warehouseFrom.setName("Ankara Depo");

        warehouseTo = new Warehouse();
        warehouseTo.setId(2L);
        warehouseTo.setName("İstanbul Depo");

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        fromStock = new Stock();
        fromStock.setId(1L);
        fromStock.setWarehouse(warehouseFrom);
        fromStock.setProduct(product);
        fromStock.setQuantity(100);

        toStock = new Stock();
        toStock.setId(2L);
        toStock.setWarehouse(warehouseTo);
        toStock.setProduct(product);
        toStock.setQuantity(20);

        transferRequest = new StockTransferRequest();
        transferRequest.setFromWarehouseId(1L);
        transferRequest.setToWarehouseId(2L);
        transferRequest.setProductId(1L);
        transferRequest.setQuantity(10);
    }

    @Test
    void transferStock_WhenSuccessful_ShouldUpdateQuantities() {
        // 1. HAZIRLIK (Arrange)
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouseFrom));
        when(warehouseRepository.findById(2L)).thenReturn(Optional.of(warehouseTo));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(stockRepository.findByWarehouseIdAndProductId(1L, 1L)).thenReturn(Optional.of(fromStock));
        when(stockRepository.findByWarehouseIdAndProductId(2L, 1L)).thenReturn(Optional.of(toStock));

        // Sahte bir kullanıcıyı SecurityContext'e yerleştir.
        // Bu, createLog metodunun hata vermesini engeller.
        UserDetails dummyUserDetails = mock(UserDetails.class);
        when(dummyUserDetails.getUsername()).thenReturn("testuser");
        Authentication authentication = new UsernamePasswordAuthenticationToken(dummyUserDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User dummyUser = new User();
        dummyUser.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(dummyUser));


        // 2. ÇALIŞTIRMA (Act)
        stockService.transferStock(transferRequest);

        // 3. DOĞRULAMA (Assert)
        assertEquals(90, fromStock.getQuantity());
        assertEquals(30, toStock.getQuantity());

        verify(stockRepository, times(2)).save(any(Stock.class));
        // Loglama metodunun çağrıldığını da doğrulayalım.
        verify(logRepository, times(1)).save(any(com.project.warehouse_stock_management_api.model.StockMovementLog.class));
    }
}