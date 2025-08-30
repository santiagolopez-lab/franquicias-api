package com.nequi.franquicias.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for updating product stock
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockRequest {
    
    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @JsonProperty("stock")
    private Integer stock;
}
