package com.project.warehouse_stock_management_api.security;

import com.project.warehouse_stock_management_api.model.User;
import com.project.warehouse_stock_management_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service // Bu sınıfın bir Spring Service'i olduğunu belirtir.
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Spring Security, bir kullanıcıyı doğrulamak istediğinde bu metodu çağırır.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Verilen username ile veritabanında kullanıcıyı ara.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Kullanıcının rollerini Spring Security'nin anladığı formata (GrantedAuthority) çevir.
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        // 3. Spring Security'nin kendi User nesnesini, bulduğumuz kullanıcı bilgileriyle oluşturup geri dön.
        // Bu nesne, kullanıcının adını, şifresini ve yetkilerini içerir.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}