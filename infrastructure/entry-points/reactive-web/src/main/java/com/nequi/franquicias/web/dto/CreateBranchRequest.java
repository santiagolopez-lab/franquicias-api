package com.nequi.franquicias.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for Branch creation request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchRequest {
    
    @NotBlank(message = "Branch name cannot be blank")
    @Size(min = 1, max = 100, message = "Branch name must be between 1 and 100 characters")
    @JsonProperty("name")
    private String name;
}
