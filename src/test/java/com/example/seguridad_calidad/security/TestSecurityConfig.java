package com.example.seguridad_calidad.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TestSecurityConfig {

    @Bean(name = "testSecurityFilterChain") // Nombre diferente para evitar conflicto
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Permitir todas las solicitudes
            );

        return http.build();
    }
}