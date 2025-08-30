package com.nequi.franquicias.usecase.franchise;

import com.nequi.franquicias.model.Franchise;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for creating a new franchise
 * Implements business logic and validation rules
 */
@RequiredArgsConstructor
public class CreateFranchiseUseCase {
    
    private final FranchiseRepository franchiseRepository;
    
    /**
     * Creates a new franchise
     * @param franchise the franchise to create
     * @return Mono of created franchise with generated ID
     */
    public Mono<Franchise> execute(Franchise franchise) {
        return validateFranchise(franchise)
                .flatMap(validFranchise -> franchiseRepository.save(validFranchise))
                .onErrorMap(this::mapError);
    }
    
    private Mono<Franchise> validateFranchise(Franchise franchise) {
        return Mono.fromCallable(() -> {
            if (franchise == null) {
                throw new BusinessValidationException("Franchise cannot be null");
            }
            
            if (franchise.getName() == null || franchise.getName().trim().isEmpty()) {
                throw new BusinessValidationException("Franchise name cannot be empty");
            }
            
            if (franchise.getName().length() > 100) {
                throw new BusinessValidationException("Franchise name cannot exceed 100 characters");
            }
            
            // Ensure ID is null for new franchise
            return Franchise.builder()
                    .name(franchise.getName().trim())
                    .branches(franchise.getBranches())
                    .build();
        });
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException) {
            return error;
        }
        return new BusinessValidationException("Error creating franchise: " + error.getMessage(), error);
    }
}
