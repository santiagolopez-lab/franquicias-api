package com.nequi.franquicias.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Franchise entity representing the root aggregate
 * Contains business rules for managing branches and products
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
    
    /**
     * Business rule: Get product with highest stock per branch
     * Returns a map where key is branch and value is the product with highest stock
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
     * Business rule: Add branch to franchise
     */
    public Franchise addBranch(Branch branch) {
        if (branch == null) {
            throw new IllegalArgumentException("Branch cannot be null");
        }
        
        List<Branch> updatedBranches = new ArrayList<>(this.branches);
        updatedBranches.add(branch);
        
        return Franchise.builder()
                .id(this.id)
                .name(this.name)
                .branches(updatedBranches)
                .build();
    }
    
    /**
     * Business rule: Find branch by ID
     */
    public Optional<Branch> findBranchById(Long branchId) {
        return branches.stream()
                .filter(branch -> branchId.equals(branch.getId()))
                .findFirst();
    }
    
    /**
     * Business rule: Update franchise name
     */
    public Franchise updateName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Franchise name cannot be empty");
        }
        return Franchise.builder()
                .id(this.id)
                .name(newName.trim())
                .branches(this.branches)
                .build();
    }
    
    /**
     * Business rule: Check if franchise has branches
     */
    public boolean hasBranches() {
        return branches != null && !branches.isEmpty();
    }
    
    /**
     * Get total number of branches
     */
    public int getBranchCount() {
        return branches != null ? branches.size() : 0;
    }
}
