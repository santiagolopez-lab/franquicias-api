package com.nequi.franquicias.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Franchise response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseResponse {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("branches")
    private List<BranchResponse> branches;
}
