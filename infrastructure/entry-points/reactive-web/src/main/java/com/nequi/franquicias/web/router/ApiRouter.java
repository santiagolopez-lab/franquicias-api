package com.nequi.franquicias.web.router;

import com.nequi.franquicias.web.handlers.BranchHandler;
import com.nequi.franquicias.web.handlers.FranchiseHandler;
import com.nequi.franquicias.web.handlers.ProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Router configuration for all API endpoints
 * Following Clean Architecture with RouterFunctions instead of @RestController
 */
@Configuration
@RequiredArgsConstructor
public class ApiRouter {
    
    private final FranchiseHandler franchiseHandler;
    private final BranchHandler branchHandler;
    private final ProductHandler productHandler;
    
    @Bean
    @RouterOperations({
        // Franchise operations
        @RouterOperation(
            path = "/api/v1/franchises",
            method = RequestMethod.POST,
            operation = @Operation(
                operationId = "createFranchise",
                summary = "Create a new franchise",
                tags = {"Franchises"},
                requestBody = @RequestBody(
                    description = "Franchise creation request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.CreateFranchiseRequest.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Franchise created successfully",
                        content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.FranchiseResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input",
                        content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.ErrorResponse.class)))
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/franchises/{franchiseId}/name",
            method = RequestMethod.PUT,
            operation = @Operation(
                operationId = "updateFranchiseName",
                summary = "Update franchise name",
                tags = {"Franchises"},
                parameters = @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true),
                requestBody = @RequestBody(
                    description = "Name update request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.UpdateNameRequest.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Franchise name updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Franchise not found")
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/franchises/{franchiseId}/top-stock-products",
            method = RequestMethod.GET,
            operation = @Operation(
                operationId = "getTopStockProductPerBranch",
                summary = "Get product with highest stock per branch",
                tags = {"Franchises"},
                parameters = @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Top stock products retrieved successfully",
                        content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.TopStockProductResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Franchise not found")
                }
            )
        )
    })
    public RouterFunction<ServerResponse> franchiseRoutes() {
        return route()
                .path("/api/v1/franchises", builder -> builder
                        .POST("", accept(MediaType.APPLICATION_JSON), franchiseHandler::createFranchise)
                        .PUT("/{franchiseId}/name", accept(MediaType.APPLICATION_JSON), franchiseHandler::updateFranchiseName)
                        .GET("/{franchiseId}/top-stock-products", franchiseHandler::getTopStockProductPerBranch)
                )
                .build();
    }
    
    @Bean
    @RouterOperations({
        // Branch operations
        @RouterOperation(
            path = "/api/v1/franchises/{franchiseId}/branches",
            method = RequestMethod.POST,
            operation = @Operation(
                operationId = "addBranchToFranchise",
                summary = "Add a new branch to a franchise",
                tags = {"Branches"},
                parameters = @Parameter(name = "franchiseId", in = ParameterIn.PATH, required = true),
                requestBody = @RequestBody(
                    description = "Branch creation request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.CreateBranchRequest.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Branch added successfully"),
                    @ApiResponse(responseCode = "404", description = "Franchise not found")
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/branches/{branchId}/name",
            method = RequestMethod.PUT,
            operation = @Operation(
                operationId = "updateBranchName",
                summary = "Update branch name",
                tags = {"Branches"},
                parameters = @Parameter(name = "branchId", in = ParameterIn.PATH, required = true),
                requestBody = @RequestBody(
                    description = "Name update request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.UpdateNameRequest.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Branch name updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
                }
            )
        )
    })
    public RouterFunction<ServerResponse> branchRoutes() {
        return route()
                .path("/api/v1", builder -> builder
                        .POST("/franchises/{franchiseId}/branches", accept(MediaType.APPLICATION_JSON), branchHandler::addBranchToFranchise)
                        .PUT("/branches/{branchId}/name", accept(MediaType.APPLICATION_JSON), branchHandler::updateBranchName)
                )
                .build();
    }
    
    @Bean
    @RouterOperations({
        // Product operations
        @RouterOperation(
            path = "/api/v1/branches/{branchId}/products",
            method = RequestMethod.POST,
            operation = @Operation(
                operationId = "addProductToBranch",
                summary = "Add a product to a branch",
                tags = {"Products"},
                parameters = @Parameter(name = "branchId", in = ParameterIn.PATH, required = true),
                requestBody = @RequestBody(
                    description = "Product creation request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.CreateProductRequest.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Product added successfully"),
                    @ApiResponse(responseCode = "404", description = "Branch not found")
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/products/{productId}",
            method = RequestMethod.DELETE,
            operation = @Operation(
                operationId = "removeProduct",
                summary = "Remove a product",
                tags = {"Products"},
                parameters = @Parameter(name = "productId", in = ParameterIn.PATH, required = true),
                responses = {
                    @ApiResponse(responseCode = "204", description = "Product removed successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/products/{productId}/stock",
            method = RequestMethod.PUT,
            operation = @Operation(
                operationId = "updateProductStock",
                summary = "Update product stock",
                tags = {"Products"},
                parameters = @Parameter(name = "productId", in = ParameterIn.PATH, required = true),
                requestBody = @RequestBody(
                    description = "Stock update request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.UpdateStockRequest.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Product stock updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
                }
            )
        ),
        @RouterOperation(
            path = "/api/v1/products/{productId}/name",
            method = RequestMethod.PUT,
            operation = @Operation(
                operationId = "updateProductName",
                summary = "Update product name",
                tags = {"Products"},
                parameters = @Parameter(name = "productId", in = ParameterIn.PATH, required = true),
                requestBody = @RequestBody(
                    description = "Name update request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = com.nequi.franquicias.web.dto.UpdateNameRequest.class))
                ),
                responses = {
                    @ApiResponse(responseCode = "200", description = "Product name updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
                }
            )
        )
    })
    public RouterFunction<ServerResponse> productRoutes() {
        return route()
                .path("/api/v1", builder -> builder
                        .POST("/branches/{branchId}/products", accept(MediaType.APPLICATION_JSON), productHandler::addProductToBranch)
                        .DELETE("/products/{productId}", productHandler::removeProduct)
                        .DELETE("/branches/{branchId}/products/{productId}", productHandler::removeProductFromBranch)
                        .PUT("/products/{productId}/stock", accept(MediaType.APPLICATION_JSON), productHandler::updateProductStock)
                        .PUT("/branches/{branchId}/products/{productId}/stock", accept(MediaType.APPLICATION_JSON), productHandler::updateProductStockInBranch)
                        .PUT("/products/{productId}/name", accept(MediaType.APPLICATION_JSON), productHandler::updateProductName)
                )
                .build();
    }
}
