package com.nequi.franquicias.usecase.franchise;

import com.nequi.franquicias.model.Franchise;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for updating franchise name
 * Extra point requirement
 */
@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {
    
    private final FranchiseRepository franchiseRepository;
    
    /**
     * Updates franchise name
     * @param franchiseId the franchise ID
     * @param newName the new name
     * @return Mono of updated franchise
     */
    public Mono<Franchise> execute(Long franchiseId, String newName) {
        return validateInput(franchiseId, newName)
                .then(franchiseRepository.findById(franchiseId))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise", franchiseId)))
                .map(franchise -> franchise.updateName(newName))
                .flatMap(franchiseRepository::update)
                .onErrorMap(this::mapError);
    }
    
    private Mono<Void> validateInput(Long franchiseId, String newName) {
        return Mono.fromRunnable(() -> {
            if (franchiseId == null) {
                throw new BusinessValidationException("Franchise ID cannot be null");
            }
            
            if (newName == null || newName.trim().isEmpty()) {
                throw new BusinessValidationException("New franchise name cannot be empty");
            }
            
            if (newName.length() > 100) {
                throw new BusinessValidationException("Franchise name cannot exceed 100 characters");
            }
        });
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error updating franchise name: " + error.getMessage(), error);
    }
}
