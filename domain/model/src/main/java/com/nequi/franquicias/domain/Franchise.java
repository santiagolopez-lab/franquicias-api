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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Franchise domain entity
 * Represents a franchise with its branches and business rules
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Franchise {
    
    private Long id;
    
    @NotBlank(message = "Franchise name cannot be blank")
    @Size(min = 1, max = 100, message = "Franchise name must be between 1 and 100 characters")
    private String name;
    
    @Builder.Default
    private List<Branch> branches = new ArrayList<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Gets the product with the highest stock for each branch
     */
    public Map<Branch, Product> getProductWithHighestStockPerBranch() {
        return branches.stream()
                .filter(Branch::hasProducts)
                .collect(Collectors.toMap(
                    branch -> branch,
                    branch -> branch.getProductWithHighestStock().orElse(null)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                ));
    }
    
    /**
     * Adds a branch to this franchise
     */
    public Franchise addBranch(Branch branch) {
        List<Branch> updatedBranches = new ArrayList<>(this.branches);
        updatedBranches.add(branch);
        
        return Franchise.builder()
                .id(this.id)
                .name(this.name)
                .branches(updatedBranches)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Updates the franchise name
     */
    public Franchise updateName(String newName) {
        return Franchise.builder()
                .id(this.id)
                .name(newName)
                .branches(this.branches)
                .createdAt(this.createdAt)
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Finds a branch by its ID
     */
    public Optional<Branch> findBranchById(Long branchId) {
        return branches.stream()
                .filter(branch -> branch.getId().equals(branchId))
                .findFirst();
    }
}