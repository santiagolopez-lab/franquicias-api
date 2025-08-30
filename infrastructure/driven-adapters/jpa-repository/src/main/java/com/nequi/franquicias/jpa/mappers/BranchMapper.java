package com.nequi.franquicias.jpa.mappers;

import com.nequi.franquicias.jpa.entities.BranchEntity;
import com.nequi.franquicias.model.Branch;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Mapper between Branch domain model and BranchEntity
 */
public class BranchMapper {
    
    /**
     * Convert domain model to entity
     * @param branch the domain model
     * @return branch entity
     */
    public static BranchEntity toEntity(Branch branch) {
        if (branch == null) {
            return null;
        }
        
        return BranchEntity.builder()
                .id(branch.getId())
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Convert entity to domain model
     * @param entity the branch entity
     * @return domain model
     */
    public static Branch toDomain(BranchEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Branch.builder()
                .id(entity.getId())
                .name(entity.getName())
                .franchiseId(entity.getFranchiseId())
                .products(new ArrayList<>()) // Products loaded separately in service
                .build();
    }
    
    /**
     * Update entity with domain values while preserving entity metadata
     * @param entity existing entity
     * @param branch domain model with updates
     * @return updated entity
     */
    public static BranchEntity updateEntity(BranchEntity entity, Branch branch) {
        if (entity == null || branch == null) {
            return entity;
        }
        
        return BranchEntity.builder()
                .id(entity.getId())
                .name(branch.getName())
                .franchiseId(entity.getFranchiseId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
