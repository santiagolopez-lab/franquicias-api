package com.nequi.franquicias.usecase.product;

import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for removing a product from a branch
 */
@RequiredArgsConstructor
public class RemoveProductFromBranchUseCase {
    
    private final ProductRepository productRepository;
    
    /**
     * Removes a product from a branch
     * @param productId the product ID to remove
     * @return Mono<Void> indicating completion
     */
    public Mono<Void> execute(Long productId) {
        return validateInput(productId)
                .then(productRepository.existsById(productId))
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product", productId)))
                .then(productRepository.deleteById(productId))
                .onErrorMap(this::mapError);
    }
    
    /**
     * Removes a product from a specific branch (with validation)
     * @param productId the product ID to remove
     * @param branchId the branch ID for validation
     * @return Mono<Void> indicating completion
     */
    public Mono<Void> execute(Long productId, Long branchId) {
        return validateInput(productId, branchId)
                .then(productRepository.existsByIdAndBranchId(productId, branchId))
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(
                    String.format("Product with ID %d not found in branch %d", productId, branchId))))
                .then(productRepository.deleteById(productId))
                .onErrorMap(this::mapError);
    }
    
    private Mono<Void> validateInput(Long productId) {
        return Mono.fromRunnable(() -> {
            if (productId == null) {
                throw new BusinessValidationException("Product ID cannot be null");
            }
        });
    }
    
    private Mono<Void> validateInput(Long productId, Long branchId) {
        return Mono.fromRunnable(() -> {
            if (productId == null) {
                throw new BusinessValidationException("Product ID cannot be null");
            }
            if (branchId == null) {
                throw new BusinessValidationException("Branch ID cannot be null");
            }
        });
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error removing product: " + error.getMessage(), error);
    }
}
