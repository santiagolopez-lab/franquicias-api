package com.nequi.franquicias.web.handlers;

import com.nequi.franquicias.usecase.product.AddProductToBranchUseCase;
import com.nequi.franquicias.usecase.product.RemoveProductFromBranchUseCase;
import com.nequi.franquicias.usecase.product.UpdateProductNameUseCase;
import com.nequi.franquicias.usecase.product.UpdateProductStockUseCase;
import com.nequi.franquicias.web.dto.CreateProductRequest;
import com.nequi.franquicias.web.dto.UpdateNameRequest;
import com.nequi.franquicias.web.dto.UpdateStockRequest;
import com.nequi.franquicias.web.mappers.ProductWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Reactive handler for Product operations
 * Following Clean Architecture principles with RouterFunctions
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductHandler {
    
    private final AddProductToBranchUseCase addProductToBranchUseCase;
    private final RemoveProductFromBranchUseCase removeProductFromBranchUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;
    
    /**
     * Add a product to a branch
     * POST /api/v1/branches/{branchId}/products
     */
    public Mono<ServerResponse> addProductToBranch(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        log.info("Adding new product to branch ID: {}", branchId);
        
        return request.bodyToMono(CreateProductRequest.class)
                .doOnNext(req -> log.debug("Create product request: {}", req))
                .map(ProductWebMapper::toDomain)
                .flatMap(product -> addProductToBranchUseCase.execute(Long.valueOf(branchId), product))
                .map(ProductWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Product added successfully to branch"))
                .doOnError(error -> log.error("Error adding product to branch: {}", error.getMessage()));
    }
    
    /**
     * Remove a product from a branch
     * DELETE /api/v1/products/{productId}
     */
    public Mono<ServerResponse> removeProduct(ServerRequest request) {
        String productId = request.pathVariable("productId");
        log.info("Removing product ID: {}", productId);
        
        return removeProductFromBranchUseCase.execute(Long.valueOf(productId))
                .then(ServerResponse
                        .noContent()
                        .build())
                .doOnSuccess(response -> log.info("Product removed successfully"))
                .doOnError(error -> log.error("Error removing product: {}", error.getMessage()));
    }
    
    /**
     * Remove a product from a specific branch (with validation)
     * DELETE /api/v1/branches/{branchId}/products/{productId}
     */
    public Mono<ServerResponse> removeProductFromBranch(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        log.info("Removing product ID: {} from branch ID: {}", productId, branchId);
        
        return removeProductFromBranchUseCase.execute(Long.valueOf(productId), Long.valueOf(branchId))
                .then(ServerResponse
                        .noContent()
                        .build())
                .doOnSuccess(response -> log.info("Product removed successfully from branch"))
                .doOnError(error -> log.error("Error removing product from branch: {}", error.getMessage()));
    }
    
    /**
     * Update product stock
     * PUT /api/v1/products/{productId}/stock
     */
    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        String productId = request.pathVariable("productId");
        log.info("Updating stock for product ID: {}", productId);
        
        return request.bodyToMono(UpdateStockRequest.class)
                .doOnNext(req -> log.debug("Update stock request: {}", req))
                .flatMap(req -> updateProductStockUseCase.execute(Long.valueOf(productId), req.getStock()))
                .map(ProductWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Product stock updated successfully"))
                .doOnError(error -> log.error("Error updating product stock: {}", error.getMessage()));
    }
    
    /**
     * Update product stock with branch validation
     * PUT /api/v1/branches/{branchId}/products/{productId}/stock
     */
    public Mono<ServerResponse> updateProductStockInBranch(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        log.info("Updating stock for product ID: {} in branch ID: {}", productId, branchId);
        
        return request.bodyToMono(UpdateStockRequest.class)
                .doOnNext(req -> log.debug("Update stock request: {}", req))
                .flatMap(req -> updateProductStockUseCase.execute(
                        Long.valueOf(productId), 
                        Long.valueOf(branchId), 
                        req.getStock()))
                .map(ProductWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Product stock updated successfully in branch"))
                .doOnError(error -> log.error("Error updating product stock in branch: {}", error.getMessage()));
    }
    
    /**
     * Update product name
     * PUT /api/v1/products/{productId}/name
     */
    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        String productId = request.pathVariable("productId");
        log.info("Updating name for product ID: {}", productId);
        
        return request.bodyToMono(UpdateNameRequest.class)
                .doOnNext(req -> log.debug("Update product name request: {}", req))
                .flatMap(req -> updateProductNameUseCase.execute(Long.valueOf(productId), req.getName()))
                .map(ProductWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Product name updated successfully"))
                .doOnError(error -> log.error("Error updating product name: {}", error.getMessage()));
    }
}
