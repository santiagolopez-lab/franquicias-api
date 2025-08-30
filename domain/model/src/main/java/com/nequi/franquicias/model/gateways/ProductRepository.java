package com.nequi.franquicias.model.gateways;

import com.nequi.franquicias.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Gateway interface for Product persistence operations
 * Following Clean Architecture principles - this is a port
 */
public interface ProductRepository {
    
    /**
     * Save a product
     * @param product the product to save
     * @return Mono of saved product with generated ID
     */
    Mono<Product> save(Product product);
    
    /**
     * Find product by ID
     * @param id the product ID
     * @return Mono of product or empty if not found
     */
    Mono<Product> findById(Long id);
    
    /**
     * Find all products by branch ID
     * @param branchId the branch ID
     * @return Flux of products belonging to the branch
     */
    Flux<Product> findByBranchId(Long branchId);
    
    /**
     * Find all products
     * @return Flux of all products
     */
    Flux<Product> findAll();
    
    /**
     * Update product
     * @param product the product to update
     * @return Mono of updated product
     */
    Mono<Product> update(Product product);
    
    /**
     * Delete product by ID
     * @param id the product ID to delete
     * @return Mono<Void> indicating completion
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Check if product exists by ID
     * @param id the product ID
     * @return Mono<Boolean> true if exists, false otherwise
     */
    Mono<Boolean> existsById(Long id);
    
    /**
     * Check if product belongs to a specific branch
     * @param productId the product ID
     * @param branchId the branch ID
     * @return Mono<Boolean> true if product belongs to branch
     */
    Mono<Boolean> existsByIdAndBranchId(Long productId, Long branchId);
    
    /**
     * Find product with highest stock in a specific branch
     * @param branchId the branch ID
     * @return Mono of product with highest stock or empty if no products
     */
    Mono<Product> findTopByBranchIdOrderByStockDesc(Long branchId);
    
    /**
     * Update product stock
     * @param productId the product ID
     * @param newStock the new stock value
     * @return Mono of updated product
     */
    Mono<Product> updateStock(Long productId, Integer newStock);
}
