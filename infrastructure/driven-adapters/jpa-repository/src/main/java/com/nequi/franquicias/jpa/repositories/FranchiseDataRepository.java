package com.nequi.franquicias.jpa.repositories;

import com.nequi.franquicias.jpa.entities.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC Repository for Franchise entities
 */
@Repository
public interface FranchiseDataRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
    
    /**
     * Find franchise by name
     * @param name the franchise name
     * @return Mono of franchise entity
     */
    Mono<FranchiseEntity> findByName(String name);
    
    /**
     * Check if franchise exists by name
     * @param name the franchise name
     * @return Mono<Boolean>
     */
    Mono<Boolean> existsByName(String name);
}
