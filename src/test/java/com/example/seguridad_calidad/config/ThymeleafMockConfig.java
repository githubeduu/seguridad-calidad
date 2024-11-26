package com.example.seguridad_calidad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.Locale;

@Configuration
public class ThymeleafMockConfig {

    @Bean
    public ViewResolver mockViewResolver() {
        return new ViewResolver() {
            @Override
            public View resolveViewName(String viewName, Locale locale) {
                return new InternalResourceView("/templates/" + viewName + ".html") {
                    @Override
                    public boolean checkResource(Locale locale) {
                        return true; // Simula que la vista existe
                    }
                };
            }
        };
    }
}
