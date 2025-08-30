package com.nequi.franquicias.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Branch repository port for Clean Architecture
 */
public interface BranchRepository {
    
    /**
     * Save a branch
     */
    Mono<Branch> save(Branch branch);
    
    /**
     * Find branch by ID
     */
    Mono<Branch> findById(Long id);
    
    /**
     * Find branches by franchise ID
     */
    Flux<Branch> findByFranchiseId(Long franchiseId);
    
    /**
     * Update branch name
     */
    Mono<Branch> updateName(Long id, String name);
    
    /**
     * Check if branch exists
     */
    Mono<Boolean> existsById(Long id);
}
