package com.nequi.franquicias.web.mappers;

import com.nequi.franquicias.model.Franchise;
import com.nequi.franquicias.web.dto.CreateFranchiseRequest;
import com.nequi.franquicias.web.dto.FranchiseResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Mapper between Franchise domain model and DTOs
 */
public class FranchiseWebMapper {
    
    /**
     * Convert CreateFranchiseRequest to domain model
     * @param request the request DTO
     * @return franchise domain model
     */
    public static Franchise toDomain(CreateFranchiseRequest request) {
        if (request == null) {
            return null;
        }
        
        return Franchise.builder()
                .name(request.getName())
                .branches(new ArrayList<>())
                .build();
    }
    
    /**
     * Convert domain model to response DTO
     * @param franchise the domain model
     * @return franchise response DTO
     */
    public static FranchiseResponse toResponse(Franchise franchise) {
        if (franchise == null) {
            return null;
        }
        
        return FranchiseResponse.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .branches(franchise.getBranches() != null ? 
                    franchise.getBranches().stream()
                        .map(BranchWebMapper::toResponse)
                        .collect(Collectors.toList()) : 
                    new ArrayList<>())
                .build();
    }
}
