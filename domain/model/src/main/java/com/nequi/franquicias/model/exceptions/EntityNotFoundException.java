package com.nequi.franquicias.model.exceptions;

/**
 * Exception thrown when a requested entity is not found
 */
public class EntityNotFoundException extends DomainException {
    
    public EntityNotFoundException(String entityName, Long id) {
        super(String.format("%s with ID %d not found", entityName, id), "ENTITY_NOT_FOUND");
    }
    
    public EntityNotFoundException(String entityName, String identifier) {
        super(String.format("%s with identifier %s not found", entityName, identifier), "ENTITY_NOT_FOUND");
    }
    
    public EntityNotFoundException(String message) {
        super(message, "ENTITY_NOT_FOUND");
    }
}
