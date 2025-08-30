package com.nequi.franquicias.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Product entity representing a product in a branch
 * Contains business rules and validations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    private Long id;
    
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    private String name;
    
    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stock;
    
    private Long branchId;
    
    /**
     * Business rule: Check if product has sufficient stock
     */
    public boolean hasStock() {
        return this.stock != null && this.stock > 0;
    }
    
    /**
     * Business rule: Check if stock is sufficient for a given quantity
     */
    public boolean hasSufficientStock(Integer requiredQuantity) {
        return this.stock != null && requiredQuantity != null && this.stock >= requiredQuantity;
    }
    
    /**
     * Business rule: Update stock ensuring it doesn't go negative
     */
    public Product updateStock(Integer newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .stock(newStock)
                .branchId(this.branchId)
                .build();
    }
    
    /**
     * Business rule: Update product name with validation
     */
    public Product updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        return Product.builder()
                .id(this.id)
                .name(newName.trim())
                .stock(this.stock)
                .branchId(this.branchId)
                .build();
    }
}
