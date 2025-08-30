package com.nequi.franquicias.web.mappers;

import com.nequi.franquicias.model.Product;
import com.nequi.franquicias.usecase.franchise.GetTopStockProductPerBranchUseCase;
import com.nequi.franquicias.web.dto.CreateProductRequest;
import com.nequi.franquicias.web.dto.ProductResponse;
import com.nequi.franquicias.web.dto.TopStockProductResponse;

/**
 * Mapper between Product domain model and DTOs
 */
public class ProductWebMapper {
    
    /**
     * Convert CreateProductRequest to domain model
     * @param request the request DTO
     * @return product domain model
     */
    public static Product toDomain(CreateProductRequest request) {
        if (request == null) {
            return null;
        }
        
        return Product.builder()
                .name(request.getName())
                .stock(request.getStock())
                .build();
    }
    
    /**
     * Convert domain model to response DTO
     * @param product the domain model
     * @return product response DTO
     */
    public static ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }
        
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .branchId(product.getBranchId())
                .build();
    }
    
    /**
     * Convert BranchTopProduct to TopStockProductResponse
     * @param branchTopProduct the use case result
     * @return top stock product response DTO
     */
    public static TopStockProductResponse toTopStockResponse(GetTopStockProductPerBranchUseCase.BranchTopProduct branchTopProduct) {
        if (branchTopProduct == null) {
            return null;
        }
        
        return TopStockProductResponse.builder()
                .branchId(branchTopProduct.getBranch() != null ? branchTopProduct.getBranch().getId() : null)
                .branchName(branchTopProduct.getBranchName())
                .productId(branchTopProduct.getProduct() != null ? branchTopProduct.getProduct().getId() : null)
                .productName(branchTopProduct.getProductName())
                .stock(branchTopProduct.getProductStock())
                .hasProduct(branchTopProduct.hasProduct())
                .build();
    }
}
