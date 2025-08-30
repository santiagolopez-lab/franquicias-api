package com.nequi.franquicias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Main application class for Franquicias API
 * Clean Architecture implementation with Spring WebFlux
 * 
 * @author Nequi Development Team
 * @version 1.0.0
 */
@SpringBootApplication(scanBasePackages = {
    "com.nequi.franquicias.config",
    "com.nequi.franquicias.web",
    "com.nequi.franquicias.jpa.repository",
    "com.nequi.franquicias.jpa.adapters"
})
@EnableR2dbcRepositories(basePackages = "com.nequi.franquicias.jpa.repositories")
public class FranquiciasApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FranquiciasApiApplication.class, args);
    }
}
