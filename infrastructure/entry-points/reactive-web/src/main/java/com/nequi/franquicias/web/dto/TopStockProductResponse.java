package com.nequi.franquicias.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Top Stock Product per Branch response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopStockProductResponse {
    
    @JsonProperty("branch_id")
    private Long branchId;
    
    @JsonProperty("branch_name")
    private String branchName;
    
    @JsonProperty("product_id")
    private Long productId;
    
    @JsonProperty("product_name")
    private String productName;
    
    @JsonProperty("stock")
    private Integer stock;
    
    @JsonProperty("has_product")
    private Boolean hasProduct;
}
