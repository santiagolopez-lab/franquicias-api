package com.nequi.franquicias.model.gateways;

import com.nequi.franquicias.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Branch persistence operations
 * Following Clean Architecture principles - this is a port
 */
public interface BranchRepository {
    
    /**
     * Save a branch
     * @param branch the branch to save
     * @return Mono of saved branch with generated ID
     */
    Mono<Branch> save(Branch branch);
    
    /**
     * Find branch by ID
     * @param id the branch ID
     * @return Mono of branch or empty if not found
     */
    Mono<Branch> findById(Long id);
    
    /**
     * Find all branches by franchise ID
     * @param franchiseId the franchise ID
     * @return Flux of branches belonging to the franchise
     */
    Flux<Branch> findByFranchiseId(Long franchiseId);
    
    /**
     * Find all branches
     * @return Flux of all branches
     */
    Flux<Branch> findAll();
    
    /**
     * Update branch
     * @param branch the branch to update
     * @return Mono of updated branch
     */
    Mono<Branch> update(Branch branch);
    
    /**
     * Delete branch by ID
     * @param id the branch ID to delete
     * @return Mono<Void> indicating completion
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Check if branch exists by ID
     * @param id the branch ID
     * @return Mono<Boolean> true if exists, false otherwise
     */
    Mono<Boolean> existsById(Long id);
    
    /**
     * Check if branch belongs to a specific franchise
     * @param branchId the branch ID
     * @param franchiseId the franchise ID
     * @return Mono<Boolean> true if branch belongs to franchise
     */
    Mono<Boolean> existsByIdAndFranchiseId(Long branchId, Long franchiseId);
}
