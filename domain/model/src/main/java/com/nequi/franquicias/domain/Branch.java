package com.nequi.franquicias.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Branch domain entity
 * Represents a branch of a franchise with its products
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    
    private Long id;
    private Long franchiseId;
    
    @NotBlank(message = "Branch name cannot be blank")
    @Size(min = 1, max = 100, message = "Branch name must be between 1 and 100 characters")
    private String name;
    
    @Builder.Default
    private List<Product> products = new ArrayList<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Checks if this branch has any products
     */
    public boolean hasProducts() {
        return products != null && !products.isEmpty();
    }
    
    /**
     * Gets the product with the highest stock in this branch
     */
    public Optional<Product> getProductWithHighestStock() {
        return products.stream()
                .max((p1, p2) -> Integer.compare(p1.getStock(), p2.getStock()));
    }
    
    /**
     * Adds a product to this branch
     */
    public Branch addProduct(Product product) {
        List<Product> updatedProducts = new ArrayList<>(this.products);
        updatedProducts.add(product);
        
        return Branch.builder()
                .id(this.id)
                .franchiseId(this.franchiseId)
                .name(this.name)
                .products(updatedProducts)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Updates the branch name
     */
    public Branch updateName(String newName) {
        return Branch.builder()
                .id(this.id)
                .franchiseId(this.franchiseId)
                .name(newName)
                .products(this.products)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Finds a product by its ID
     */
    public Optional<Product> findProductById(Long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }
    
    /**
     * Removes a product from this branch
     */
    public Branch removeProduct(Long productId) {
        List<Product> updatedProducts = this.products.stream()
                .filter(product -> !product.getId().equals(productId))
                .toList();
        
        return Branch.builder()
                .id(this.id)
                .franchiseId(this.franchiseId)
                .name(this.name)
                .products(updatedProducts)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
