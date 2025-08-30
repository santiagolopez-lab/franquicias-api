package com.nequi.franquicias.model.exceptions;

/**
 * Exception thrown when business validation rules are violated
 */
public class BusinessValidationException extends DomainException {
    
    public BusinessValidationException(String message) {
        super(message, "BUSINESS_VALIDATION_ERROR");
    }
    
    public BusinessValidationException(String message, Throwable cause) {
        super(message, "BUSINESS_VALIDATION_ERROR", cause);
    }
}
