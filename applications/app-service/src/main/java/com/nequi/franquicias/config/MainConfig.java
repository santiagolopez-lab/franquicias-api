package com.nequi.franquicias.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Main application configuration that imports all necessary configurations
 */
@Configuration
@Import({
    UseCaseConfig.class,
    DatabaseConfig.class,
    CorsConfig.class
})
public class MainConfig {
}
