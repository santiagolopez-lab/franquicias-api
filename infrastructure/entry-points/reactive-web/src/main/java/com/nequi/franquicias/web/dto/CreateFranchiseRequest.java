package com.nequi.franquicias.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for Franchise creation request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFranchiseRequest {
    
    @NotBlank(message = "Franchise name cannot be blank")
    @Size(min = 1, max = 100, message = "Franchise name must be between 1 and 100 characters")
    @JsonProperty("name")
    private String name;
}
