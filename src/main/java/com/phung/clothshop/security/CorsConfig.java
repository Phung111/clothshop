package com.phung.clothshop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:8080",
                        "http://localhost:8081",
                        "http://localhost:27002",
                        "http://localhost:27003",
                        "http://localhost:27004",
                        "https://nhahangsentrang.top",
                        "https://cp.nhahangsentrang.top",
                        "https://socket.nhahangsentrang.top")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
