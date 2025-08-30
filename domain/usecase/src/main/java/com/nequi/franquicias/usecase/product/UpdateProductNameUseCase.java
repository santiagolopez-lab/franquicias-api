package com.nequi.franquicias.usecase.product;

import com.nequi.franquicias.model.Product;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for updating product name
 * Extra point requirement
 */
@RequiredArgsConstructor
public class UpdateProductNameUseCase {
    
    private final ProductRepository productRepository;
    
    /**
     * Updates product name
     * @param productId the product ID
     * @param newName the new name
     * @return Mono of updated product
     */
    public Mono<Product> execute(Long productId, String newName) {
        return validateInput(productId, newName)
                .then(productRepository.findById(productId))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product", productId)))
                .map(product -> product.updateName(newName))
                .flatMap(productRepository::update)
                .onErrorMap(this::mapError);
    }
    
    private Mono<Void> validateInput(Long productId, String newName) {
        return Mono.fromRunnable(() -> {
            if (productId == null) {
                throw new BusinessValidationException("Product ID cannot be null");
            }
            
            if (newName == null || newName.trim().isEmpty()) {
                throw new BusinessValidationException("New product name cannot be empty");
            }
            
            if (newName.length() > 100) {
                throw new BusinessValidationException("Product name cannot exceed 100 characters");
            }
        });
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error updating product name: " + error.getMessage(), error);
    }
}
