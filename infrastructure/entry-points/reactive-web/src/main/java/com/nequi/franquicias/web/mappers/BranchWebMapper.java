package com.nequi.franquicias.web.mappers;

import com.nequi.franquicias.model.Branch;
import com.nequi.franquicias.web.dto.BranchResponse;
import com.nequi.franquicias.web.dto.CreateBranchRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Mapper between Branch domain model and DTOs
 */
public class BranchWebMapper {
    
    /**
     * Convert CreateBranchRequest to domain model
     * @param request the request DTO
     * @return branch domain model
     */
    public static Branch toDomain(CreateBranchRequest request) {
        if (request == null) {
            return null;
        }
        
        return Branch.builder()
                .name(request.getName())
                .products(new ArrayList<>())
                .build();
    }
    
    /**
     * Convert domain model to response DTO
     * @param branch the domain model
     * @return branch response DTO
     */
    public static BranchResponse toResponse(Branch branch) {
        if (branch == null) {
            return null;
        }
        
        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .products(branch.getProducts() != null ? 
                    branch.getProducts().stream()
                        .map(ProductWebMapper::toResponse)
                        .collect(Collectors.toList()) : 
                    new ArrayList<>())
                .build();
    }
}
