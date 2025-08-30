package com.nequi.franquicias.jpa.adapters;

import com.nequi.franquicias.jpa.mappers.BranchMapper;
import com.nequi.franquicias.jpa.repositories.BranchDataRepository;
import com.nequi.franquicias.model.Branch;
import com.nequi.franquicias.model.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * R2DBC implementation of BranchRepository
 * Adapter pattern implementation for Clean Architecture
 */
@Repository
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepository {
    
    private final BranchDataRepository branchDataRepository;
    
    @Override
    public Mono<Branch> save(Branch branch) {
        return Mono.fromCallable(() -> BranchMapper.toEntity(branch))
                .flatMap(branchDataRepository::save)
                .map(BranchMapper::toDomain);
    }
    
    @Override
    public Mono<Branch> findById(Long id) {
        return branchDataRepository.findById(id)
                .map(BranchMapper::toDomain);
    }
    
    @Override
    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        return branchDataRepository.findByFranchiseId(franchiseId)
                .map(BranchMapper::toDomain);
    }
    
    @Override
    public Flux<Branch> findAll() {
        return branchDataRepository.findAll()
                .map(BranchMapper::toDomain);
    }
    
    @Override
    public Mono<Branch> update(Branch branch) {
        return branchDataRepository.findById(branch.getId())
                .map(existingEntity -> BranchMapper.updateEntity(existingEntity, branch))
                .flatMap(branchDataRepository::save)
                .map(BranchMapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return branchDataRepository.deleteById(id);
    }
    
    @Override
    public Mono<Boolean> existsById(Long id) {
        return branchDataRepository.existsById(id);
    }
    
    @Override
    public Mono<Boolean> existsByIdAndFranchiseId(Long branchId, Long franchiseId) {
        return branchDataRepository.existsByIdAndFranchiseId(branchId, franchiseId);
    }
}
