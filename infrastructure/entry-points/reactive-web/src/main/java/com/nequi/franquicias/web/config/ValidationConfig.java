package com.nequi.franquicias.web.config;

import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Validation configuration for reactive web layer
 */
@Configuration
public class ValidationConfig {
    
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
