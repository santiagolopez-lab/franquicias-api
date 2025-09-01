package com.nequi.franquicias.config;

import com.nequi.franquicias.model.gateways.BranchRepository;
import com.nequi.franquicias.model.gateways.FranchiseRepository;
import com.nequi.franquicias.model.gateways.ProductRepository;
import com.nequi.franquicias.usecase.branch.AddBranchToFranchiseUseCase;
import com.nequi.franquicias.usecase.branch.UpdateBranchNameUseCase;
import com.nequi.franquicias.usecase.franchise.CreateFranchiseUseCase;
import com.nequi.franquicias.usecase.franchise.GetAllFranchisesUseCase;
import com.nequi.franquicias.usecase.franchise.GetTopStockProductPerBranchUseCase;
import com.nequi.franquicias.usecase.franchise.UpdateFranchiseNameUseCase;
import com.nequi.franquicias.usecase.product.AddProductToBranchUseCase;
import com.nequi.franquicias.usecase.product.RemoveProductFromBranchUseCase;
import com.nequi.franquicias.usecase.product.UpdateProductNameUseCase;
import com.nequi.franquicias.usecase.product.UpdateProductStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Use Case configuration beans
 * Clean Architecture wiring of business logic components
 */
@Configuration
public class UseCaseConfig {
    
    // ============ Franchise Use Cases ============
    
    @Bean
    public CreateFranchiseUseCase createFranchiseUseCase(
            FranchiseRepository franchiseRepository) {
        return new CreateFranchiseUseCase(franchiseRepository);
    }
    
    @Bean
    public GetAllFranchisesUseCase getAllFranchisesUseCase(
            FranchiseRepository franchiseRepository,
            BranchRepository branchRepository) {
        return new GetAllFranchisesUseCase(franchiseRepository, branchRepository);
    }
    
    @Bean
    public UpdateFranchiseNameUseCase updateFranchiseNameUseCase(
            FranchiseRepository franchiseRepository) {
        return new UpdateFranchiseNameUseCase(franchiseRepository);
    }
    
    @Bean
    public GetTopStockProductPerBranchUseCase getTopStockProductPerBranchUseCase(
            FranchiseRepository franchiseRepository,
            BranchRepository branchRepository,
            ProductRepository productRepository) {
        return new GetTopStockProductPerBranchUseCase(
                franchiseRepository, 
                branchRepository, 
                productRepository);
    }
    
    // ============ Branch Use Cases ============
    
    @Bean
    public AddBranchToFranchiseUseCase addBranchToFranchiseUseCase(
            BranchRepository branchRepository,
            FranchiseRepository franchiseRepository) {
        return new AddBranchToFranchiseUseCase(branchRepository, franchiseRepository);
    }
    
    @Bean
    public UpdateBranchNameUseCase updateBranchNameUseCase(
            BranchRepository branchRepository) {
        return new UpdateBranchNameUseCase(branchRepository);
    }
    
    // ============ Product Use Cases ============
    
    @Bean
    public AddProductToBranchUseCase addProductToBranchUseCase(
            ProductRepository productRepository,
            BranchRepository branchRepository) {
        return new AddProductToBranchUseCase(productRepository, branchRepository);
    }
    
    @Bean
    public RemoveProductFromBranchUseCase removeProductFromBranchUseCase(
            ProductRepository productRepository) {
        return new RemoveProductFromBranchUseCase(productRepository);
    }
    
    @Bean
    public UpdateProductStockUseCase updateProductStockUseCase(
            ProductRepository productRepository) {
        return new UpdateProductStockUseCase(productRepository);
    }
    
    @Bean
    public UpdateProductNameUseCase updateProductNameUseCase(
            ProductRepository productRepository) {
        return new UpdateProductNameUseCase(productRepository);
    }
}
