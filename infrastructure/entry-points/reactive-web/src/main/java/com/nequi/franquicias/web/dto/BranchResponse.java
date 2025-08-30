package com.nequi.franquicias.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Branch response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("franchise_id")
    private Long franchiseId;
    
    @JsonProperty("products")
    private List<ProductResponse> products;
}
