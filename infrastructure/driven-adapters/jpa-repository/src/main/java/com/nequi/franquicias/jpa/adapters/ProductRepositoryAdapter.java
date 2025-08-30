package com.nequi.franquicias.jpa.adapters;

import com.nequi.franquicias.jpa.mappers.ProductMapper;
import com.nequi.franquicias.jpa.repositories.ProductDataRepository;
import com.nequi.franquicias.model.Product;
import com.nequi.franquicias.model.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * R2DBC implementation of ProductRepository
 * Adapter pattern implementation for Clean Architecture
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    
    private final ProductDataRepository productDataRepository;
    
    @Override
    public Mono<Product> save(Product product) {
        return Mono.fromCallable(() -> ProductMapper.toEntity(product))
                .flatMap(productDataRepository::save)
                .map(ProductMapper::toDomain);
    }
    
    @Override
    public Mono<Product> findById(Long id) {
        return productDataRepository.findById(id)
                .map(ProductMapper::toDomain);
    }
    
    @Override
    public Flux<Product> findByBranchId(Long branchId) {
        return productDataRepository.findByBranchId(branchId)
                .map(ProductMapper::toDomain);
    }
    
    @Override
    public Flux<Product> findAll() {
        return productDataRepository.findAll()
                .map(ProductMapper::toDomain);
    }
    
    @Override
    public Mono<Product> update(Product product) {
        return productDataRepository.findById(product.getId())
                .map(existingEntity -> ProductMapper.updateEntity(existingEntity, product))
                .flatMap(productDataRepository::save)
                .map(ProductMapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return productDataRepository.deleteById(id);
    }
    
    @Override
    public Mono<Boolean> existsById(Long id) {
        return productDataRepository.existsById(id);
    }
    
    @Override
    public Mono<Boolean> existsByIdAndBranchId(Long productId, Long branchId) {
        return productDataRepository.existsByIdAndBranchId(productId, branchId);
    }
    
    @Override
    public Mono<Product> findTopByBranchIdOrderByStockDesc(Long branchId) {
        return productDataRepository.findTopByBranchIdOrderByStockDesc(branchId)
                .map(ProductMapper::toDomain);
    }
    
    @Override
    public Mono<Product> updateStock(Long productId, Integer newStock) {
        return productDataRepository.updateStockById(productId, newStock)
                .then(productDataRepository.findById(productId))
                .map(ProductMapper::toDomain);
    }
}
