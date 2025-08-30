package com.nequi.franquicias.jpa.adapters;

import com.nequi.franquicias.jpa.mappers.FranchiseMapper;
import com.nequi.franquicias.jpa.repositories.FranchiseDataRepository;
import com.nequi.franquicias.model.Franchise;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * R2DBC implementation of FranchiseRepository
 * Adapter pattern implementation for Clean Architecture
 */
@Repository
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {
    
    private final FranchiseDataRepository franchiseDataRepository;
    
    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return Mono.fromCallable(() -> FranchiseMapper.toEntity(franchise))
                .flatMap(franchiseDataRepository::save)
                .map(FranchiseMapper::toDomain);
    }
    
    @Override
    public Mono<Franchise> findById(Long id) {
        return franchiseDataRepository.findById(id)
                .map(FranchiseMapper::toDomain);
    }
    
    @Override
    public Flux<Franchise> findAll() {
        return franchiseDataRepository.findAll()
                .map(FranchiseMapper::toDomain);
    }
    
    @Override
    public Mono<Franchise> update(Franchise franchise) {
        return franchiseDataRepository.findById(franchise.getId())
                .map(existingEntity -> FranchiseMapper.updateEntity(existingEntity, franchise))
                .flatMap(franchiseDataRepository::save)
                .map(FranchiseMapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return franchiseDataRepository.deleteById(id);
    }
    
    @Override
    public Mono<Boolean> existsById(Long id) {
        return franchiseDataRepository.existsById(id);
    }
}
