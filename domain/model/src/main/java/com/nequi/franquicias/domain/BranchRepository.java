package com.nequi.franquicias.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for Branch domain entity
 * Following clean architecture principles
 */
public interface BranchRepository {
    
    /**
     * Saves a branch
     */
    Mono<Branch> save(Branch branch);
    
    /**
     * Finds a branch by its ID
     */
    Mono<Branch> findById(Long id);
    
    /**
     * Finds all branches for a franchise
     */
    Flux<Branch> findByFranchiseId(Long franchiseId);
    
    /**
     * Updates a branch name
     */
    Mono<Branch> updateName(Long id, String name);
    
    /**
     * Checks if a branch exists by ID
     */
    Mono<Boolean> existsById(Long id);
}
