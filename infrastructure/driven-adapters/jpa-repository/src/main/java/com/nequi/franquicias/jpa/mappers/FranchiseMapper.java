package com.nequi.franquicias.jpa.mappers;

import com.nequi.franquicias.jpa.entities.FranchiseEntity;
import com.nequi.franquicias.model.Franchise;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Mapper between Franchise domain model and FranchiseEntity
 */
public class FranchiseMapper {
    
    /**
     * Convert domain model to entity
     * @param franchise the domain model
     * @return franchise entity
     */
    public static FranchiseEntity toEntity(Franchise franchise) {
        if (franchise == null) {
            return null;
        }
        
        return FranchiseEntity.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Convert entity to domain model
     * @param entity the franchise entity
     * @return domain model
     */
    public static Franchise toDomain(FranchiseEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Franchise.builder()
                .id(entity.getId())
                .name(entity.getName())
                .branches(new ArrayList<>()) // Branches loaded separately in service
                .build();
    }
    
    /**
     * Update entity with domain values while preserving entity metadata
     * @param entity existing entity
     * @param franchise domain model with updates
     * @return updated entity
     */
    public static FranchiseEntity updateEntity(FranchiseEntity entity, Franchise franchise) {
        if (entity == null || franchise == null) {
            return entity;
        }
        
        return FranchiseEntity.builder()
                .id(entity.getId())
                .name(franchise.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
