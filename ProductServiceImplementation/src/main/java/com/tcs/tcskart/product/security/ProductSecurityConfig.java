package com.tcs.tcskart.product.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class ProductSecurityConfig {

    private final ProductServiceJwtFilter jwtFilter;

    public ProductSecurityConfig(ProductServiceJwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/products/all","/products/name/**","/products/product/**","/products/updateQuantity/**").permitAll()
                .requestMatchers("/products/add").hasRole("ADMIN")
                .requestMatchers("/products/update/**").hasRole("ADMIN")
                .requestMatchers("/products/delete/**").hasRole("ADMIN")
                .requestMatchers("/products/review/**").hasRole("CUSTOMER")
                .anyRequest().permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
