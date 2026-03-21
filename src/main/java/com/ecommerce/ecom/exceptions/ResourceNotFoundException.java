package com.ecommerce.ecom.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resource;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resource, String fieldName, Long fieldId) {
        super(String.format("%s not found with %s: %s", resource, fieldName, fieldId));
        this.resource = resource;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }
}

