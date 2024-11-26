package com.example.seguridad_calidad.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;


import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private HttpSession session;
    private final String secretKey = System.getenv("JWT_SECRET_KEY");  

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        session = mock(HttpSession.class);

        // Simular que la clave JWT está configurada
        System.setProperty("JWT_SECRET_KEY", secretKey);
    }

    @Test
    void doFilterInternal_NoToken() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("token")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verificar que el filtro continúa sin autenticar
        verify(filterChain, times(1)).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Test
    void doFilterInternal_InvalidToken() throws Exception {
        when(request.getSession()).thenReturn(session);

        // Proveer un token con una firma incorrecta
        String invalidToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl19.invalid-signature";
        when(session.getAttribute("token")).thenReturn(invalidToken);

        // Ejecutar el filtro
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verificar que el filtro continúa sin autenticar
        verify(filterChain, times(1)).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    
}