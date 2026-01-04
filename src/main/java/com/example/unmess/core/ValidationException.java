package com.example.unmess.core;

/**
 * Exception thrown when validation fails.
 * Provides detailed context about validation failures.
 * 
 * @author Photo Editor Engineering Team
 * @version 1.0.0
 */
public class ValidationException extends Exception {
    
    private final String fieldName;
    private final Object invalidValue;
    
    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
        this.invalidValue = null;
    }
    
    public ValidationException(String message, String fieldName, Object invalidValue) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = null;
        this.invalidValue = null;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public Object getInvalidValue() {
        return invalidValue;
    }
    
    @Override
    public String toString() {
        if (fieldName != null) {
            return String.format("ValidationException: %s [Field: %s, Value: %s]", 
                getMessage(), fieldName, invalidValue);
        }
        return super.toString();
    }
}
