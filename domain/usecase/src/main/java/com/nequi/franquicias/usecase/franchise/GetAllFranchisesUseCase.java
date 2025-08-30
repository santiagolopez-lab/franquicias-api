package com.nequi.franquicias.usecase.franchise;

import com.nequi.franquicias.model.Franchise;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Use case for retrieving all franchises
 * Following Clean Architecture principles
 */
@RequiredArgsConstructor
public class GetAllFranchisesUseCase {
    
    private final FranchiseRepository franchiseRepository;
    
    /**
     * Retrieves all franchises from the repository
     * 
     * @return Flux of all franchises
     */
    public Flux<Franchise> execute() {
        return franchiseRepository.findAll();
    }
}
