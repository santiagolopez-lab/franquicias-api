package com.nequi.franquicias.web.handlers;

import com.nequi.franquicias.usecase.branch.AddBranchToFranchiseUseCase;
import com.nequi.franquicias.usecase.branch.UpdateBranchNameUseCase;
import com.nequi.franquicias.web.dto.CreateBranchRequest;
import com.nequi.franquicias.web.dto.UpdateNameRequest;
import com.nequi.franquicias.web.mappers.BranchWebMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Reactive handler for Branch operations
 * Following Clean Architecture principles with RouterFunctions
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BranchHandler {
    
    private final AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    
    /**
     * Add a new branch to a franchise
     * POST /api/v1/franchises/{franchiseId}/branches
     */
    public Mono<ServerResponse> addBranchToFranchise(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        log.info("Adding new branch to franchise ID: {}", franchiseId);
        
        return request.bodyToMono(CreateBranchRequest.class)
                .doOnNext(req -> log.debug("Create branch request: {}", req))
                .map(BranchWebMapper::toDomain)
                .flatMap(branch -> addBranchToFranchiseUseCase.execute(Long.valueOf(franchiseId), branch))
                .map(BranchWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Branch added successfully to franchise"))
                .doOnError(error -> log.error("Error adding branch to franchise: {}", error.getMessage()));
    }
    
    /**
     * Update branch name
     * PUT /api/v1/branches/{branchId}/name
     */
    public Mono<ServerResponse> updateBranchName(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        log.info("Updating branch name for ID: {}", branchId);
        
        return request.bodyToMono(UpdateNameRequest.class)
                .doOnNext(req -> log.debug("Update branch name request: {}", req))
                .flatMap(req -> updateBranchNameUseCase.execute(Long.valueOf(branchId), req.getName()))
                .map(BranchWebMapper::toResponse)
                .flatMap(response -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("Branch name updated successfully"))
                .doOnError(error -> log.error("Error updating branch name: {}", error.getMessage()));
    }
}
