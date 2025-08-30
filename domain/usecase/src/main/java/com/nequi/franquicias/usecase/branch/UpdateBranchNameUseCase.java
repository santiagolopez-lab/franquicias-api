package com.nequi.franquicias.usecase.branch;

import com.nequi.franquicias.model.Branch;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for updating branch name
 * Extra point requirement
 */
@RequiredArgsConstructor
public class UpdateBranchNameUseCase {
    
    private final BranchRepository branchRepository;
    
    /**
     * Updates branch name
     * @param branchId the branch ID
     * @param newName the new name
     * @return Mono of updated branch
     */
    public Mono<Branch> execute(Long branchId, String newName) {
        return validateInput(branchId, newName)
                .then(branchRepository.findById(branchId))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Branch", branchId)))
                .map(branch -> branch.updateName(newName))
                .flatMap(branchRepository::update)
                .onErrorMap(this::mapError);
    }
    
    private Mono<Void> validateInput(Long branchId, String newName) {
        return Mono.fromRunnable(() -> {
            if (branchId == null) {
                throw new BusinessValidationException("Branch ID cannot be null");
            }
            
            if (newName == null || newName.trim().isEmpty()) {
                throw new BusinessValidationException("New branch name cannot be empty");
            }
            
            if (newName.length() > 100) {
                throw new BusinessValidationException("Branch name cannot exceed 100 characters");
            }
        });
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error updating branch name: " + error.getMessage(), error);
    }
}
