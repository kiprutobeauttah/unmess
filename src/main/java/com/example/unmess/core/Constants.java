package com.example.unmess.core;

/**
 * System-wide constants and configuration parameters.
 * 
 * @author Photo Editor Engineering Team
 * @version 1.0.0
 * @since 2026-01-03
 */
public final class Constants {
    
    // Prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class cannot be instantiated");
    }
    
    // Application Metadata
    public static final String APP_NAME = "Advanced Photo Editor";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_VENDOR = "Photo Editor Engineering";
    
    // Image Processing Limits
    public static final int MAX_IMAGE_WIDTH = 8192;
    public static final int MAX_IMAGE_HEIGHT = 8192;
    public static final long MAX_IMAGE_SIZE_BYTES = 100 * 1024 * 1024; // 100MB
    public static final int MAX_HISTORY_SIZE = 20;
    
    // Adjustment Ranges
    public static final double BRIGHTNESS_MIN = -1.0;
    public static final double BRIGHTNESS_MAX = 1.0;
    public static final double CONTRAST_MIN = -1.0;
    public static final double CONTRAST_MAX = 1.0;
    public static final double SATURATION_MIN = -1.0;
    public static final double SATURATION_MAX = 1.0;
    public static final double BLUR_MIN = 0.0;
    public static final double BLUR_MAX = 10.0;
    public static final double SHARPEN_MIN = 0.0;
    public static final double SHARPEN_MAX = 3.0;
    
    // Color Space Constants
    public static final double GRAYSCALE_RED_WEIGHT = 0.299;
    public static final double GRAYSCALE_GREEN_WEIGHT = 0.587;
    public static final double GRAYSCALE_BLUE_WEIGHT = 0.114;
    
    // UI Constants
    public static final int DEFAULT_WINDOW_WIDTH = 1400;
    public static final int DEFAULT_WINDOW_HEIGHT = 900;
    public static final int CONTROL_PANEL_WIDTH = 320;
    public static final int MIN_WINDOW_WIDTH = 800;
    public static final int MIN_WINDOW_HEIGHT = 600;
    
    // File Format Support
    public static final String[] SUPPORTED_READ_FORMATS = {"png", "jpg", "jpeg", "gif", "bmp"};
    public static final String[] SUPPORTED_WRITE_FORMATS = {"png", "jpg", "jpeg"};
    
    // Performance Tuning
    public static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final int PIXEL_BATCH_SIZE = 1000;
    
    // Error Messages
    public static final String ERR_NO_IMAGE_LOADED = "No image is currently loaded";
    public static final String ERR_IMAGE_TOO_LARGE = "Image exceeds maximum dimensions";
    public static final String ERR_INVALID_FORMAT = "Unsupported image format";
    public static final String ERR_IO_FAILURE = "Failed to read/write image file";
}
