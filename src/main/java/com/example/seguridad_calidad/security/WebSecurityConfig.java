package com.example.seguridad_calidad.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/login").permitAll() // Permitir acceso a /login sin autenticación
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/registro").permitAll()
                        .requestMatchers("/**.css").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/recetas").authenticated() // Proteger acceso a /recetas y subrutas
                        .requestMatchers("/recetas/**").authenticated()
                        .requestMatchers("/mantenedor-usuarios").authenticated()
                        .anyRequest().permitAll()

                )
                .addFilterBefore(new JwtAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si el token está manejado manualmente
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/login");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> response.sendRedirect("/login");
    }
}
