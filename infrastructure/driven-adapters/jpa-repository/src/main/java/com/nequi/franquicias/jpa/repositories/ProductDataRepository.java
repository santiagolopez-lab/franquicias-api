package com.nequi.franquicias.jpa.repositories;

import com.nequi.franquicias.jpa.entities.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC Repository for Product entities
 */
@Repository
public interface ProductDataRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    
    /**
     * Find all products by branch ID
     * @param branchId the branch ID
     * @return Flux of product entities
     */
    Flux<ProductEntity> findByBranchId(Long branchId);
    
    /**
     * Check if product exists by ID and branch ID
     * @param id the product ID
     * @param branchId the branch ID
     * @return Mono<Boolean>
     */
    Mono<Boolean> existsByIdAndBranchId(Long id, Long branchId);
    
    /**
     * Find product with highest stock in a specific branch
     * @param branchId the branch ID
     * @return Mono of product entity with highest stock
     */
    @Query("SELECT * FROM products WHERE branch_id = :branchId ORDER BY stock DESC LIMIT 1")
    Mono<ProductEntity> findTopByBranchIdOrderByStockDesc(Long branchId);
    
    /**
     * Find product by name and branch ID
     * @param name the product name
     * @param branchId the branch ID
     * @return Mono of product entity
     */
    Mono<ProductEntity> findByNameAndBranchId(String name, Long branchId);
    
    /**
     * Update product stock
     * @param id the product ID
     * @param stock the new stock value
     * @return Mono<Integer> number of affected rows
     */
    @Query("UPDATE products SET stock = :stock, updated_at = NOW() WHERE id = :id")
    Mono<Integer> updateStockById(Long id, Integer stock);
}
