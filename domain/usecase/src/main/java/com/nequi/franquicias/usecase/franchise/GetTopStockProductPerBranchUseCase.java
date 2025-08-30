package com.nequi.franquicias.usecase.franchise;

import com.nequi.franquicias.model.Branch;
import com.nequi.franquicias.model.Product;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.BranchRepository;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import com.nequi.franquicias.model.gateways.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Use case for getting the product with highest stock per branch for a specific franchise
 * This is a key requirement (#7)
 */
@RequiredArgsConstructor
public class GetTopStockProductPerBranchUseCase {
    
    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    
    /**
     * Gets the product with highest stock per branch for a specific franchise
     * @param franchiseId the franchise ID
     * @return Flux of BranchTopProduct containing branch and its top product
     */
    public Flux<BranchTopProduct> execute(Long franchiseId) {
        return validateInput(franchiseId)
                .then(franchiseRepository.existsById(franchiseId))
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise", franchiseId)))
                .thenMany(branchRepository.findByFranchiseId(franchiseId))
                .flatMap(this::getTopProductForBranch)
                .onErrorMap(this::mapError);
    }
    
    private Mono<BranchTopProduct> getTopProductForBranch(Branch branch) {
        return productRepository.findTopByBranchIdOrderByStockDesc(branch.getId())
                .map(product -> BranchTopProduct.builder()
                        .branch(branch)
                        .product(product)
                        .build())
                .switchIfEmpty(Mono.just(BranchTopProduct.builder()
                        .branch(branch)
                        .product(null) // No products in this branch
                        .build()));
    }
    
    private Mono<Void> validateInput(Long franchiseId) {
        return Mono.fromRunnable(() -> {
            if (franchiseId == null) {
                throw new BusinessValidationException("Franchise ID cannot be null");
            }
        });
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error getting top stock products: " + error.getMessage(), error);
    }
    
    /**
     * Data transfer object for branch and its top product
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BranchTopProduct {
        private Branch branch;
        private Product product; // null if branch has no products
        
        public boolean hasProduct() {
            return product != null;
        }
        
        public String getBranchName() {
            return branch != null ? branch.getName() : null;
        }
        
        public String getProductName() {
            return product != null ? product.getName() : null;
        }
        
        public Integer getProductStock() {
            return product != null ? product.getStock() : null;
        }
    }
}
