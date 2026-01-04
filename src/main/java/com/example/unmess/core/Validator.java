package com.example.unmess.core;

import javafx.scene.image.Image;

/**
 * Centralized validation system for all input parameters.
 * Ensures data integrity and prevents invalid operations.
 * 
 * @author Photo Editor Engineering Team
 * @version 1.0.0
 */
public final class Validator {
    
    private Validator() {
        throw new AssertionError("Validator class cannot be instantiated");
    }
    
    /**
     * Validate that an object is not null
     */
    public static <T> T requireNonNull(T obj, String paramName) throws ValidationException {
        if (obj == null) {
            throw new ValidationException("Parameter cannot be null", paramName, null);
        }
        return obj;
    }
    
    /**
     * Validate that a value is within a specified range
     */
    public static double requireInRange(double value, double min, double max, String paramName) 
            throws ValidationException {
        if (value < min || value > max) {
            throw new ValidationException(
                String.format("Value must be between %.2f and %.2f", min, max),
                paramName, value
            );
        }
        return value;
    }
    
    /**
     * Validate that an integer is within a specified range
     */
    public static int requireInRange(int value, int min, int max, String paramName) 
            throws ValidationException {
        if (value < min || value > max) {
            throw new ValidationException(
                String.format("Value must be between %d and %d", min, max),
                paramName, value
            );
        }
        return value;
    }
    
    /**
     * Validate that an integer is positive
     */
    public static int requirePositive(int value, String paramName) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException("Value must be positive", paramName, value);
        }
        return value;
    }
    
    /**
     * Validate image dimensions
     */
    public static void validateImageDimensions(Image image) throws ValidationException {
        requireNonNull(image, "image");
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        
        if (width <= 0 || height <= 0) {
            throw new ValidationException("Image has invalid dimensions: " + width + "x" + height);
        }
        
        if (width > Constants.MAX_IMAGE_WIDTH || height > Constants.MAX_IMAGE_HEIGHT) {
            throw new ValidationException(
                String.format("Image dimensions (%dx%d) exceed maximum allowed (%dx%d)",
                    width, height, Constants.MAX_IMAGE_WIDTH, Constants.MAX_IMAGE_HEIGHT)
            );
        }
    }
    
    /**
     * Validate adjustment parameter
     */
    public static double validateAdjustment(double value, double min, double max, String name) 
            throws ValidationException {
        return requireInRange(value, min, max, name);
    }
    
    /**
     * Validate file extension
     */
    public static String validateFileExtension(String extension, String[] allowedExtensions) 
            throws ValidationException {
        requireNonNull(extension, "extension");
        
        String lowerExt = extension.toLowerCase();
        for (String allowed : allowedExtensions) {
            if (lowerExt.equals(allowed.toLowerCase())) {
                return lowerExt;
            }
        }
        
        throw new ValidationException(
            "Unsupported file format: " + extension,
            "extension", extension
        );
    }
}
