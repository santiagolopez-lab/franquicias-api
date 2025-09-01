package com.nequi.franquicias.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for Franchise domain entity
 * Following clean architecture principles
 */
public interface FranchiseRepository {
    
    /**
     * Saves a franchise
     */
    Mono<Franchise> save(Franchise franchise);
    
    /**
     * Finds a franchise by its ID
     */
    Mono<Franchise> findById(Long id);
    
    /**
     * Finds all franchises
     */
    Flux<Franchise> findAll();
    
    /**
     * Updates a franchise name
     */
    Mono<Franchise> updateName(Long id, String name);
    
    /**
     * Checks if a franchise exists by ID
     */
    Mono<Boolean> existsById(Long id);
}
