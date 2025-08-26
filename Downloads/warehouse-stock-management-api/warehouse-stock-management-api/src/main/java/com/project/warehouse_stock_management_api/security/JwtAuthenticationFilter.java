package com.project.warehouse_stock_management_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Gelen istekte "Authorization" başlığı var mı ve "Bearer " ile başlıyor mu diye kontrol et.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Token yoksa, isteği zincirdeki bir sonraki filtreye devret ve çık.
            return;
        }

        // 2. "Bearer " kısmını atlayarak sadece token'ı al.
        jwt = authHeader.substring(7);
        username = jwtUtil.extractUsername(jwt);

        // 3. Token'dan kullanıcı adını alabildiysek VE bu kullanıcı daha önce doğrulanmadıysa...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 4. Veritabanından kullanıcı detaylarını yükle.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 5. Token geçerli mi diye kontrol et.
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // 6. Eğer token geçerliyse, Spring Security için bir kimlik doğrulama nesnesi oluştur.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Oluşturulan kimlik doğrulama nesnesini SecurityContext'e yerleştir.
                // Bu, kullanıcının artık "giriş yapmış" olarak kabul edildiği andır.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 8. İsteği, zincirdeki bir sonraki filtreye devret.
        filterChain.doFilter(request, response);
    }
}