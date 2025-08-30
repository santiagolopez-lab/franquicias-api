package com.nequi.franquicias.jpa.repositories;

import com.nequi.franquicias.jpa.entities.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC Repository for Branch entities
 */
@Repository
public interface BranchDataRepository extends ReactiveCrudRepository<BranchEntity, Long> {
    
    /**
     * Find all branches by franchise ID
     * @param franchiseId the franchise ID
     * @return Flux of branch entities
     */
    Flux<BranchEntity> findByFranchiseId(Long franchiseId);
    
    /**
     * Check if branch exists by ID and franchise ID
     * @param id the branch ID
     * @param franchiseId the franchise ID
     * @return Mono<Boolean>
     */
    Mono<Boolean> existsByIdAndFranchiseId(Long id, Long franchiseId);
    
    /**
     * Find branch by name and franchise ID
     * @param name the branch name
     * @param franchiseId the franchise ID
     * @return Mono of branch entity
     */
    Mono<BranchEntity> findByNameAndFranchiseId(String name, Long franchiseId);
}
