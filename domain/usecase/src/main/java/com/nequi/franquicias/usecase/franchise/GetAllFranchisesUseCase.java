package com.nequi.franquicias.usecase.franchise;

import com.nequi.franquicias.model.Franchise;
import com.nequi.franquicias.model.gateways.BranchRepository;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * Use case for retrieving all franchises with their branches
 * Following Clean Architecture principles
 */
@RequiredArgsConstructor
@Slf4j
public class GetAllFranchisesUseCase {
    
    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;
    
    /**
     * Retrieves all franchises from the repository with their associated branches
     * 
     * @return Flux of all franchises with branches loaded
     */
    public Flux<Franchise> execute() {
        return franchiseRepository.findAll()
                .flatMap(this::loadBranches);
    }
    
    /**
     * Load branches for a franchise
     * @param franchise the franchise without branches
     * @return franchise with loaded branches
     */
    private Mono<Franchise> loadBranches(Franchise franchise) {
        log.info("Loading branches for franchise ID: {}", franchise.getId());
        return branchRepository.findByFranchiseId(franchise.getId())
                .doOnNext(branch -> log.info("Found branch: {} for franchise: {}", branch.getName(), franchise.getId()))
                .collectList()
                .doOnNext(branches -> log.info("Total branches found for franchise {}: {}", franchise.getId(), branches.size()))
                .map(branches -> {
                    return Franchise.builder()
                            .id(franchise.getId())
                            .name(franchise.getName())
                            .branches(branches)
                            .build();
                })
                .onErrorReturn(Franchise.builder()
                        .id(franchise.getId())
                        .name(franchise.getName())
                        .branches(new ArrayList<>())
                        .build());
    }
}
