package com.nequi.franquicias.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Franchise repository port for Clean Architecture
 */
public interface FranchiseRepository {
    
    /**
     * Save a franchise
     */
    Mono<Franchise> save(Franchise franchise);
    
    /**
     * Find franchise by ID
     */
    Mono<Franchise> findById(Long id);
    
    /**
     * Find all franchises
     */
    Flux<Franchise> findAll();
    
    /**
     * Update franchise name
     */
    Mono<Franchise> updateName(Long id, String name);
    
    /**
     * Check if franchise exists
     */
    Mono<Boolean> existsById(Long id);
}
