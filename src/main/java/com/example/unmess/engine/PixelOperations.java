package com.example.unmess.engine;

/**
 * Low-level pixel manipulation utilities.
 * Provides optimized color space operations and clamping.
 * 
 * Performance: Inline-optimized methods for hot path execution
 * 
 * @author Photo Editor Engineering Team
 * @version 1.0.0
 */
public final class PixelOperations {
    
    private PixelOperations() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
    
    /**
     * Clamp value to [0.0, 1.0] range
     * Optimized for color channel values
     */
    public static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
    
    /**
     * Clamp integer value to specified range
     */
    public static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Extract red channel from ARGB integer
     */
    public static int getRed(int argb) {
        return (argb >> 16) & 0xFF;
    }
    
    /**
     * Extract green channel from ARGB integer
     */
    public static int getGreen(int argb) {
        return (argb >> 8) & 0xFF;
    }
    
    /**
     * Extract blue channel from ARGB integer
     */
    public static int getBlue(int argb) {
        return argb & 0xFF;
    }
    
    /**
     * Extract alpha channel from ARGB integer
     */
    public static int getAlpha(int argb) {
        return (argb >> 24) & 0xFF;
    }
    
    /**
     * Compose ARGB integer from channels
     */
    public static int toArgb(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
    
    /**
     * Convert RGB to grayscale using luminosity method
     * Uses ITU-R BT.709 coefficients for accurate perception
     */
    public static double toGrayscale(double r, double g, double b) {
        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }
    
    /**
     * Linear interpolation between two values
     */
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * clamp(t);
    }
    
    /**
     * Convert byte [0-255] to normalized double [0.0-1.0]
     */
    public static double normalize(int byteValue) {
        return byteValue / 255.0;
    }
    
    /**
     * Convert normalized double [0.0-1.0] to byte [0-255]
     */
    public static int denormalize(double normalizedValue) {
        return (int) (clamp(normalizedValue) * 255.0);
    }
}
