package com.nequi.franquicias.web.exception;

import com.nequi.franquicias.model.exceptions.BusinessValidationException;
import com.nequi.franquicias.model.exceptions.DomainException;
import com.nequi.franquicias.model.exceptions.EntityNotFoundException;
import com.nequi.franquicias.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * Global exception handler for reactive web layer
 * Handles all exceptions and converts them to appropriate HTTP responses
 */
@Component
@Order(-2)
@Slf4j
public class GlobalExceptionHandler implements WebExceptionHandler {
    
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("Error processing request: {}", ex.getMessage(), ex);
        
        ResponseEntity<ErrorResponse> responseEntity = buildErrorResponse(ex, exchange);
        
        exchange.getResponse().setStatusCode(responseEntity.getStatusCode());
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        
        return exchange.getResponse().writeWith(
            Mono.just(exchange.getResponse().bufferFactory().wrap(
                responseEntity.getBody().toString().getBytes()
            ))
        );
    }
    
    private ResponseEntity<ErrorResponse> buildErrorResponse(Throwable ex, ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();
        
        if (ex instanceof EntityNotFoundException) {
            return buildNotFoundResponse((EntityNotFoundException) ex, path);
        }
        
        if (ex instanceof BusinessValidationException) {
            return buildBadRequestResponse((BusinessValidationException) ex, path);
        }
        
        if (ex instanceof WebExchangeBindException) {
            return buildValidationErrorResponse((WebExchangeBindException) ex, path);
        }
        
        if (ex instanceof DomainException) {
            return buildDomainErrorResponse((DomainException) ex, path);
        }
        
        return buildInternalErrorResponse(ex, path);
    }
    
    private ResponseEntity<ErrorResponse> buildNotFoundResponse(EntityNotFoundException ex, String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(path)
                .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    private ResponseEntity<ErrorResponse> buildBadRequestResponse(BusinessValidationException ex, String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Business Validation Error")
                .message(ex.getMessage())
                .path(path)
                .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    private ResponseEntity<ErrorResponse> buildValidationErrorResponse(WebExchangeBindException ex, String path) {
        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .rejectedValue(error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Input validation failed")
                .path(path)
                .errors(fieldErrors)
                .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    private ResponseEntity<ErrorResponse> buildDomainErrorResponse(DomainException ex, String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Domain Error")
                .message(ex.getMessage())
                .path(path)
                .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    private ResponseEntity<ErrorResponse> buildInternalErrorResponse(Throwable ex, String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred")
                .path(path)
                .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
