package com.project.warehouse_stock_management_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bu metot, şifreleri şifrelemek için kullanılacak olan BCrypt algoritmasını
    // Spring context'ine bir "Bean" olarak ekler.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bu metot, uygulamanın güvenlik kurallarını tanımlar.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF korumasını API'ler için genellikle devre dışı bırakırız.
                .csrf(csrf -> csrf.disable())
                // Gelen isteklere yetkilendirme kurallarını uygula
                .authorizeHttpRequests(auth -> auth
                        // "/api/auth/**" ile başlayan tüm adreslere (örn: /api/auth/login, /api/auth/register)
                        // herkesin erişimine izin ver.
                        .requestMatchers("/api/auth/**").permitAll()
                        // Yukarıdaki kural dışındaki diğer tüm isteklere
                        // sadece kimliği doğrulanmış (giriş yapmış) kullanıcıların erişmesine izin ver.
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}