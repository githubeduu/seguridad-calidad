package com.example.seguridad_calidad;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SeguridadCalidadApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verificar que el contexto se carga correctamente
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void testBeanLoads() {
        // Verificar que un bean específico está cargado (ajusta según tu configuración)
        assertThat(applicationContext.containsBean("seguridadCalidadApplication")).isTrue();
    }

    @Test
    void testMain() {
        // Ejecutar el método main
        SeguridadCalidadApplication.main(new String[] {});
    }
}
