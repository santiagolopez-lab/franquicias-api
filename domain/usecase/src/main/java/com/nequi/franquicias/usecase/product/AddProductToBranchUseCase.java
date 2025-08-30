package com.nequi.franquicias.usecase.product;

import com.nequi.franquicias.model.Product;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.BranchRepository;
import com.nequi.franquicias.model.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for adding a product to a specific branch
 */
@RequiredArgsConstructor
public class AddProductToBranchUseCase {
    
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    
    /**
     * Adds a product to a branch
     * @param branchId the branch ID
     * @param product the product to add
     * @return Mono of created product with generated ID
     */
    public Mono<Product> execute(Long branchId, Product product) {
        return validateInput(branchId, product)
                .then(branchRepository.existsById(branchId))
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch", branchId)))
                .then(Mono.fromCallable(() -> prepareProduct(branchId, product)))
                .flatMap(productRepository::save)
                .onErrorMap(this::mapError);
    }
    
    private Mono<Void> validateInput(Long branchId, Product product) {
        return Mono.fromRunnable(() -> {
            if (branchId == null) {
                throw new BusinessValidationException("Branch ID cannot be null");
            }
            
            if (product == null) {
                throw new BusinessValidationException("Product cannot be null");
            }
            
            if (product.getName() == null || product.getName().trim().isEmpty()) {
                throw new BusinessValidationException("Product name cannot be empty");
            }
            
            if (product.getName().length() > 100) {
                throw new BusinessValidationException("Product name cannot exceed 100 characters");
            }
            
            if (product.getStock() == null) {
                throw new BusinessValidationException("Product stock cannot be null");
            }
            
            if (product.getStock() < 0) {
                throw new BusinessValidationException("Product stock cannot be negative");
            }
        });
    }
    
    private Product prepareProduct(Long branchId, Product product) {
        return Product.builder()
                .name(product.getName().trim())
                .stock(product.getStock())
                .branchId(branchId)
                .build();
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error adding product to branch: " + error.getMessage(), error);
    }
}
