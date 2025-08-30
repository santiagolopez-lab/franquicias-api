package com.nequi.franquicias.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Branch entity representing a branch of a franchise
 * Contains business rules for managing products
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    
    private Long id;
    
    @NotBlank(message = "Branch name cannot be blank")
    @Size(min = 1, max = 100, message = "Branch name must be between 1 and 100 characters")
    private String name;
    
    private Long franchiseId;
    
    @Builder.Default
    private List<Product> products = new ArrayList<>();
    
    /**
     * Business rule: Find product with highest stock in this branch
     */
    public Optional<Product> getProductWithHighestStock() {
        return products.stream()
                .filter(Product::hasStock)
                .max((p1, p2) -> Integer.compare(p1.getStock(), p2.getStock()));
    }
    
    /**
     * Business rule: Add product to branch
     */
    public Branch addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        List<Product> updatedProducts = new ArrayList<>(this.products);
        updatedProducts.add(product);
        
        return Branch.builder()
                .id(this.id)
                .name(this.name)
                .franchiseId(this.franchiseId)
                .products(updatedProducts)
                .build();
    }
    
    /**
     * Business rule: Remove product from branch
     */
    public Branch removeProduct(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        
        List<Product> updatedProducts = this.products.stream()
                .filter(product -> !productId.equals(product.getId()))
                .toList();
        
        return Branch.builder()
                .id(this.id)
                .name(this.name)
                .franchiseId(this.franchiseId)
                .products(updatedProducts)
                .build();
    }
    
    /**
     * Business rule: Find product by ID
     */
    public Optional<Product> findProductById(Long productId) {
        return products.stream()
                .filter(product -> productId.equals(product.getId()))
                .findFirst();
    }
    
    /**
     * Business rule: Update branch name
     */
    public Branch updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Branch name cannot be empty");
        }
        return Branch.builder()
                .id(this.id)
                .name(newName.trim())
                .franchiseId(this.franchiseId)
                .products(this.products)
                .build();
    }
    
    /**
     * Business rule: Check if branch has any products
     */
    public boolean hasProducts() {
        return products != null && !products.isEmpty();
    }
}
