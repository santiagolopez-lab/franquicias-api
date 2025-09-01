package com.nequi.franquicias.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * Product domain entity
 * Represents a product in a branch with stock management
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    private Long id;
    private Long branchId;
    
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    private String name;
    
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Updates the product stock
     */
    public Product updateStock(Integer newStock) {
        return Product.builder()
                .id(this.id)
                .branchId(this.branchId)
                .name(this.name)
                .stock(newStock)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Updates the product name
     */
    public Product updateName(String newName) {
        return Product.builder()
                .id(this.id)
                .branchId(this.branchId)
                .name(newName)
                .stock(this.stock)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Checks if this product has stock available
     */
    public boolean hasStock() {
        return stock != null && stock > 0;
    }
    
    /**
     * Checks if this product has enough stock for a given quantity
     */
    public boolean hasStock(Integer requiredQuantity) {
        return stock != null && stock >= requiredQuantity;
    }
}
