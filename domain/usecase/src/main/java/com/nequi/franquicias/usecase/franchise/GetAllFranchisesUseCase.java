package com.nequi.franquicias.usecase.franchise;

import com.nequi.franquicias.model.Branch;
import com.nequi.franquicias.model.Franchise;
import com.nequi.franquicias.model.gateways.BranchRepository;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import com.nequi.franquicias.model.gateways.ProductRepository;
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
    private final ProductRepository productRepository;
    
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
                .flatMap(this::loadProducts) // Load products for each branch
                .doOnNext(branch -> log.info("Found branch: {} with {} products for franchise: {}", 
                    branch.getName(), branch.getProducts().size(), franchise.getId()))
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
    
    /**
     * Load products for a branch
     * @param branch the branch without products
     * @return branch with loaded products
     */
    private Mono<Branch> loadProducts(Branch branch) {
        log.debug("Loading products for branch ID: {}", branch.getId());
        return productRepository.findByBranchId(branch.getId())
                .collectList()
                .map(products -> {
                    return Branch.builder()
                            .id(branch.getId())
                            .name(branch.getName())
                            .franchiseId(branch.getFranchiseId())
                            .products(products)
                            .build();
                })
                .onErrorReturn(Branch.builder()
                        .id(branch.getId())
                        .name(branch.getName())
                        .franchiseId(branch.getFranchiseId())
                        .products(new ArrayList<>())
                        .build());
    }
}
