package com.nequi.franquicias.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for Product domain entity
 * Following clean architecture principles
 */
public interface ProductRepository {
    
    /**
     * Saves a product
     */
    Mono<Product> save(Product product);
    
    /**
     * Finds a product by its ID
     */
    Mono<Product> findById(Long id);
    
    /**
     * Finds all products for a branch
     */
    Flux<Product> findByBranchId(Long branchId);
    
    /**
     * Updates a product stock
     */
    Mono<Product> updateStock(Long id, Integer stock);
    
    /**
     * Updates a product name
     */
    Mono<Product> updateName(Long id, String name);
    
    /**
     * Deletes a product by ID
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Checks if a product exists by ID
     */
    Mono<Boolean> existsById(Long id);
    
    /**
     * Finds the product with highest stock for each branch in a franchise
     */
    Flux<Product> findTopStockProductsByFranchiseId(Long franchiseId);
}
