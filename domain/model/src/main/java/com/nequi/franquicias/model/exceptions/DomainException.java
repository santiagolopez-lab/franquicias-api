package com.nequi.franquicias.model.exceptions;

/**
 * Base domain exception for business rule violations
 */
public class DomainException extends RuntimeException {
    
    private final String errorCode;
    
    public DomainException(String message) {
        super(message);
        this.errorCode = "DOMAIN_ERROR";
    }
    
    public DomainException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "DOMAIN_ERROR";
    }
    
    public DomainException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
