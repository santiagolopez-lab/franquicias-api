package com.nequi.franquicias.usecase.branch;

import com.nequi.franquicias.model.Branch;
import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.model.gateways.BranchRepository;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for adding a branch to an existing franchise
 */
@RequiredArgsConstructor
public class AddBranchToFranchiseUseCase {
    
    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;
    
    /**
     * Adds a branch to a franchise
     * @param franchiseId the franchise ID
     * @param branch the branch to add
     * @return Mono of created branch with generated ID
     */
    public Mono<Branch> execute(Long franchiseId, Branch branch) {
        return validateInput(franchiseId, branch)
                .then(franchiseRepository.existsById(franchiseId))
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise", franchiseId)))
                .then(Mono.fromCallable(() -> prepareBranch(franchiseId, branch)))
                .flatMap(branchRepository::save)
                .onErrorMap(this::mapError);
    }
    
    private Mono<Void> validateInput(Long franchiseId, Branch branch) {
        return Mono.fromRunnable(() -> {
            if (franchiseId == null) {
                throw new BusinessValidationException("Franchise ID cannot be null");
            }
            
            if (branch == null) {
                throw new BusinessValidationException("Branch cannot be null");
            }
            
            if (branch.getName() == null || branch.getName().trim().isEmpty()) {
                throw new BusinessValidationException("Branch name cannot be empty");
            }
            
            if (branch.getName().length() > 100) {
                throw new BusinessValidationException("Branch name cannot exceed 100 characters");
            }
        });
    }
    
    private Branch prepareBranch(Long franchiseId, Branch branch) {
        return Branch.builder()
                .name(branch.getName().trim())
                .franchiseId(franchiseId)
                .products(branch.getProducts())
                .build();
    }
    
    private Throwable mapError(Throwable error) {
        if (error instanceof BusinessValidationException || error instanceof EntityNotFoundException) {
            return error;
        }
        return new BusinessValidationException("Error adding branch to franchise: " + error.getMessage(), error);
    }
}
