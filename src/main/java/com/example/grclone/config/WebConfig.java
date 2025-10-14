package com.example.grclone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class WebConfig {
    
    @Bean
    PageableHandlerMethodArgumentResolverCustomizer customizePagination() {
        return resolver -> {
            resolver.setMaxPageSize(3);
            resolver.setFallbackPageable(PageRequest.of(0,15));
        };
    }
}
