package com.nequi.franquicias.jpa.mappers;

import com.nequi.franquicias.jpa.entities.ProductEntity;
import com.nequi.franquicias.model.Product;

import java.time.LocalDateTime;

/**
 * Mapper between Product domain model and ProductEntity
 */
public class ProductMapper {
    
    /**
     * Convert domain model to entity
     * @param product the domain model
     * @return product entity
     */
    public static ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }
        
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .branchId(product.getBranchId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Convert entity to domain model
     * @param entity the product entity
     * @return domain model
     */
    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .stock(entity.getStock())
                .branchId(entity.getBranchId())
                .build();
    }
    
    /**
     * Update entity with domain values while preserving entity metadata
     * @param entity existing entity
     * @param product domain model with updates
     * @return updated entity
     */
    public static ProductEntity updateEntity(ProductEntity entity, Product product) {
        if (entity == null || product == null) {
            return entity;
        }
        
        return ProductEntity.builder()
                .id(entity.getId())
                .name(product.getName())
                .stock(product.getStock())
                .branchId(entity.getBranchId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
