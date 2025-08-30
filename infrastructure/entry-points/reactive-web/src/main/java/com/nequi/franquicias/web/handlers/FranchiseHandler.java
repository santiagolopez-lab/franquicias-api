package com.nequi.franquicias.web.handlers;

import com.nequi.franquicias.usecase.franchise.CreateFranchiseUseCase;
import com.nequi.franquicias.usecase.franchise.GetTopStockProductPerBranchUseCase;
import com.nequi.franquicias.usecase.franchise.UpdateFranchiseNameUseCase;
import com.nequi.franquicias.web.dto.CreateFranchiseRequest;
import com.nequi.franquicias.web.dto.UpdateNameRequest;
import com.nequi.franquicias.web.mappers.FranchiseWebMapper;
import com.nequi.franquicias.web.mappers.ProductWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Reactive handler for Franchise operations
 * Following Clean Architecture principles with RouterFunctions
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseHandler {
    
    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final GetTopStockProductPerBranchUseCase getTopStockProductPerBranchUseCase;
    
    /**
     * Create a new franchise
     * POST /api/v1/franchises
     */
    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        log.info("Creating new franchise");
        
        return request.bodyToMono(CreateFranchiseRequest.class)
                .doOnNext(req -> log.debug("Create franchise request: {}", req))
                .map(FranchiseWebMapper::toDomain)
                .flatMap(createFranchiseUseCase::execute)
                .map(FranchiseWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Franchise created successfully"))
                .doOnError(error -> log.error("Error creating franchise: {}", error.getMessage()));
    }
    
    /**
     * Update franchise name
     * PUT /api/v1/franchises/{franchiseId}/name
     */
    public Mono<ServerResponse> updateFranchiseName(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        log.info("Updating franchise name for ID: {}", franchiseId);
        
        return request.bodyToMono(UpdateNameRequest.class)
                .doOnNext(req -> log.debug("Update franchise name request: {}", req))
                .flatMap(req -> updateFranchiseNameUseCase.execute(Long.valueOf(franchiseId), req.getName()))
                .map(FranchiseWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Franchise name updated successfully"))
                .doOnError(error -> log.error("Error updating franchise name: {}", error.getMessage()));
    }
    
    /**
     * Get product with highest stock per branch for a franchise
     * GET /api/v1/franchises/{franchiseId}/top-stock-products
     */
    public Mono<ServerResponse> getTopStockProductPerBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        log.info("Getting top stock products per branch for franchise ID: {}", franchiseId);
        
        return getTopStockProductPerBranchUseCase.execute(Long.valueOf(franchiseId))
                .map(ProductWebMapper::toTopStockResponse)
                .collectList()
                .flatMap(responseList -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseList))
                .doOnSuccess(response -> log.info("Top stock products retrieved successfully"))
                .doOnError(error -> log.error("Error getting top stock products: {}", error.getMessage()));
    }
}
