package com.nequi.franquicias.model.gateways;

import com.nequi.franquicias.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Franchise persistence operations
 * Following Clean Architecture principles - this is a port
 */
public interface FranchiseRepository {
    
    /**
     * Save a franchise
     * @param franchise the franchise to save
     * @return Mono of saved franchise with generated ID
     */
    Mono<Franchise> save(Franchise franchise);
    
    /**
     * Find franchise by ID
     * @param id the franchise ID
     * @return Mono of franchise or empty if not found
     */
    Mono<Franchise> findById(Long id);
    
    /**
     * Find all franchises
     * @return Flux of all franchises
     */
    Flux<Franchise> findAll();
    
    /**
     * Update franchise
     * @param franchise the franchise to update
     * @return Mono of updated franchise
     */
    Mono<Franchise> update(Franchise franchise);
    
    /**
     * Delete franchise by ID
     * @param id the franchise ID to delete
     * @return Mono<Void> indicating completion
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Check if franchise exists by ID
     * @param id the franchise ID
     * @return Mono<Boolean> true if exists, false otherwise
     */
    Mono<Boolean> existsById(Long id);
}
