package com.nequi.franquicias.usecase.product;

import com.nequi.franquicias.model.Product;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for updating product stock
 */
@RequiredArgsConstructor
public class UpdateProductStockUseCase {
    
    private final ProductRepository productRepository;
    
    /**
     * Updates product stock
     * @param productId the product ID
     * @param newStock the new stock value
     * @return Mono of updated product
     */
    public Mono<Product> execute(Long productId, Integer newStock) {
        return validateInput(productId, newStock)
                .then(productRepository.findById(productId))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product", productId)))
                .map(product -> product.updateStock(newStock))
                .flatMap(productRepository::update)
                .onErrorMap(this::mapError);
    }
    
    /**
     * Updates product stock with branch validation
     * @param productId the product ID
     * @param branchId the branch ID for validation
     * @param newStock the new stock value
     * @return Mono of updated product
     */
    public Mono<Product> execute(Long productId, Long branchId, Integer newStock) {
        return validateInput(productId, branchId, newStock)
                .then(productRepository.existsByIdAndBranchId(productId, branchId))
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new EntityNotFoundException(
                    String.format("Product with ID %d not found in branch %d", productId, branchId))))
                .then(productRepository.findById(productId))
                .map(product -> product.updateStock(newStock))
                .flatMap(productRepository::update)
                .onErrorMap(this::mapError);
    }
    
    private Mono<Void> validateInput(Long productId, Integer newStock) {
        return Mono.fromRunnable(() -> {
            if (productId == null) {
                throw new BusinessValidationException("Product ID cannot be null");
            }
            if (newStock == null) {
                throw new BusinessValidationException("New stock value cannot be null");
            }
            if (newStock < 0) {
                throw new BusinessValidationException("Stock cannot be negative");
            }
        });
    }
    
    private Mono<Void> validateInput(Long productId, Long branchId, Integer newStock) {
        return Mono.fromRunnable(() -> {
            if (productId == null) {
                throw new BusinessValidationException("Product ID cannot be null");
            }
            if (branchId == null) {
                throw new BusinessValidationException("Branch ID cannot be null");
            }
            if (newStock == null) {
                throw new BusinessValidationException("New stock value cannot be null");
            }
            if (newStock < 0) {
                throw new BusinessValidationException("Stock cannot be negative");
            }
        });
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error updating product stock: " + error.getMessage(), error);
    }
}
