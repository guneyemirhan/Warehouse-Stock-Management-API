package com.project.warehouse_stock_management_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component // Bu sınıfın bir Spring bileşeni olduğunu ve başka sınıflara enjekte edilebileceğini belirtir.
public class JwtUtil {

    // Token'ı imzalamak için kullanılacak gizli anahtar. Bu anahtar çok önemlidir ve kimseyle paylaşılmamalıdır.
    // Değerini application.properties dosyasından alacağız.
    @Value("${jwt.secret}")
    private String secret;

    // Token'ın geçerlilik süresi (milisaniye cinsinden). Değerini application.properties dosyasından alacağız.
    @Value("${jwt.expiration}")
    private long expiration;

    // --- TOKEN OLUŞTURMA ---

    // Kullanıcı bilgileriyle bir JWT oluşturur.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // --- TOKEN DOĞRULAMA VE AYRIŞTIRMA ---

    // Token'dan kullanıcı adını (subject) çıkarır.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Token'dan son geçerlilik tarihini çıkarır.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token'ın geçerli olup olmadığını kontrol eder (kullanıcı adı doğru ve süresi dolmamış mı?).
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Token'ın süresinin dolup dolmadığını kontrol eder.
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token içindeki belirli bir "claim"i (bilgiyi) çıkarmak için genel bir metot.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Token içindeki tüm bilgileri (claims) çıkarır.
    private Claims extractAllClaims(String token) {
        return Jwts.parser() // <-- DEĞİŞİKLİK BURADA
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    // Gizli anahtarı, Jwts kütüphanesinin anlayacağı formata dönüştürür.
    private SecretKey getSigningKey() { // <-- Dönüş tipi SecretKey oldu
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}