# Warehouse & Stock Management API

Bu proje, Java Spring Boot kullanılarak geliştirilmiş bir Depo ve Stok Yönetimi RESTful API'sidir. Depolar, ürünler, kategoriler ve stokların yönetilmesini; kullanıcı kimlik doğrulaması ve yetkilendirme işlemlerini ve tüm stok hareketlerinin loglanmasını sağlar.

## Teknolojiler

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA (Hibernate)**: Veritabanı işlemleri için.
- **Spring Security**: Güvenlik ve JWT tabanlı kimlik doğrulama için.
- **PostgreSQL**: İlişkisel veritabanı olarak.
- **Maven**: Bağımlılık yönetimi için.
- **Lombok**: Kod tekrarını azaltmak için.
- **JWT (JSON Web Token)**: API güvenliği için.
- **Springdoc OpenAPI (Swagger)**: API dokümantasyonu için.

## API Özellikleri

- **Depo Yönetimi:** Yeni depo ekleme, listeleme.
- **Ürün ve Kategori Yönetimi:** Hiyerarşik kategoriler ve ürünler için tam CRUD işlemleri.
- **Kullanıcı Yönetimi:** JWT tabanlı kullanıcı kaydı (`/register`) ve girişi (`/login`).
- **Stok Yönetimi:** Depolara stok girişi ve depolar arası stok transferi.
- **Loglama:** Tüm stok hareketlerinin denetim kaydının tutulması.
- **Raporlama:** Depo bazlı stok durumu raporu.

## Kurulum ve Çalıştırma

1.  **Projeyi Klonlayın:**
    ```bash
    git clone https://github.com/guneyemirhan/Warehouse-Stock-Management-API.git
    ```
2.  **Veritabanını Ayarlayın:**
    - Bilgisayarınızda PostgreSQL'in kurulu olduğundan emin olun.
    - `wms_db` adında yeni bir veritabanı oluşturun.
    - `src/main/resources/application.properties` dosyasını açın ve `spring.datasource.username` ile `spring.datasource.password` alanlarını kendi PostgreSQL bilgilerinizle güncelleyin.

3.  **Uygulamayı Çalıştırın:**
    - Projeyi favori IDE'nizde (örn: IntelliJ IDEA) açın.
    - Maven bağımlılıklarının yüklenmesini bekleyin.
    - `WarehouseStockManagementApiApplication.java` sınıfını çalıştırın.
    - Uygulama varsayılan olarak `http://localhost:8080` adresinde çalışmaya başlayacaktır.

## API Dokümantasyonu (Swagger)

Uygulama çalıştıktan sonra, tüm API endpoint'lerini ve test arayüzünü görmek için aşağıdaki adresi tarayıcınızda açabilirsiniz:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)