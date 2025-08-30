package com.nequi.franquicias.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Database configuration for schema management
 * Flyway integration for R2DBC environments
 */
@Configuration
public class DatabaseConfig {
    
    @Value("${spring.flyway.url}")
    private String flywayUrl;
    
    @Value("${spring.flyway.user}")
    private String flywayUser;
    
    @Value("${spring.flyway.password}")
    private String flywayPassword;
    
    @Value("${spring.flyway.locations:classpath:db/migration}")
    private String flywayLocations;
    
    @Value("${spring.flyway.enabled:true}")
    private boolean flywayEnabled;
    
    /**
     * Run Flyway migrations after application startup
     * This is needed because R2DBC doesn't support Flyway auto-configuration
     */
    @EventListener(ApplicationReadyEvent.class)
    public void runFlywayMigrations() {
        if (!flywayEnabled) {
            return;
        }
        
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(flywayUrl, flywayUser, flywayPassword)
                    .locations(flywayLocations)
                    .baselineOnMigrate(true)
                    .load();
            
            flyway.migrate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Flyway migrations", e);
        }
    }
}
