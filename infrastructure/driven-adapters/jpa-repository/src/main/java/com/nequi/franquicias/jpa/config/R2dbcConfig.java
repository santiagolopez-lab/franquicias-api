package com.nequi.franquicias.jpa.config;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.Duration;

/**
 * R2DBC Configuration for MySQL connection
 * Production-ready configuration with connection pooling
 */
@Configuration
@EnableR2dbcRepositories(basePackages = "com.nequi.franquicias.jpa.repositories")
public class R2dbcConfig extends AbstractR2dbcConfiguration {
    
    @Value("${spring.r2dbc.host:localhost}")
    private String host;
    
    @Value("${spring.r2dbc.port:3306}")
    private int port;
    
    @Value("${spring.r2dbc.database:franquicias_db}")
    private String database;
    
    @Value("${spring.r2dbc.username:root}")
    private String username;
    
    @Value("${spring.r2dbc.password:password}")
    private String password;
    
    @Value("${spring.r2dbc.pool.initial-size:10}")
    private int initialSize;
    
    @Value("${spring.r2dbc.pool.max-size:20}")
    private int maxSize;
    
    @Value("${spring.r2dbc.pool.max-idle-time:30m}")
    private Duration maxIdleTime;
    
    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        MySqlConnectionConfiguration configuration = MySqlConnectionConfiguration.builder()
                .host(host)
                .port(port)
                .database(database)
                .username(username)
                .password(password)
                .build();
        
        MySqlConnectionFactory connectionFactory = MySqlConnectionFactory.from(configuration);
        
        // Connection pooling configuration
        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
                .maxIdleTime(maxIdleTime)
                .initialSize(initialSize)
                .maxSize(maxSize)
                .validationQuery("SELECT 1")
                .build();
        
        return new ConnectionPool(poolConfiguration);
    }
}
