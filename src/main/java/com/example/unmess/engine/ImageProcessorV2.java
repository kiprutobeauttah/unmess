package com.example.unmess.engine;

import com.example.unmess.core.Constants;
import com.example.unmess.core.Logger;
import com.example.unmess.core.ValidationException;
import com.example.unmess.core.Validator;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Advanced image processing engine with validation, logging, and error handling.
 * 
 * Architecture:
 * - Immutable operations (input images are never modified)
 * - Comprehensive validation of all parameters
 * - Performance logging for optimization
 * - Thread-safe design
 * 
 * Performance Characteristics:
 * - Brightness/Contrast/Saturation: O(width * height)
 * - Blur: O(width * height * radius^2)
 * - Sharpen: O(width * height * 9)
 * 
 * @author Photo Editor Engineering Team
 * @version 2.0.0
 */
public final class ImageProcessorV2 {
    
    private ImageProcessorV2() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
    
    /**
     * Adjust image brightness.
     * 
     * Algorithm: Linear adjustment of RGB channels
     * Complexity: O(width * height)
     * 
     * @param image Source image (not modified)
     * @param factor Brightness adjustment [-1.0 to 1.0]
     *               -1.0 = completely black
     *                0.0 = no change
     *                1.0 = completely white
     * @return New image with adjusted brightness
     * @throws ValidationException if parameters are invalid
     */
    public static WritableImage adjustBrightness(Image image, double factor) 
            throws ValidationException {
        
        Validator.requireNonNull(image, "image");
        Validator.validateAdjustment(factor, 
            Constants.BRIGHTNESS_MIN, Constants.BRIGHTNESS_MAX, "brightness");
        
        long startTime = System.currentTimeMillis();
        Logger.debug(String.format("Adjusting brightness: factor=%.2f", factor));
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double r = PixelOperations.clamp(color.getRed() + factor);
                double g = PixelOperations.clamp(color.getGreen() + factor);
                double b = PixelOperations.clamp(color.getBlue() + factor);
                writer.setColor(x, y, new Color(r, g, b, color.getOpacity()));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Brightness adjusted in %dms", elapsed));
        
        return result;
    }

    /**
     * Adjust image contrast.
     * 
     * Algorithm: Scales deviation from middle gray (0.5)
     * Complexity: O(width * height)
     * 
     * @param image Source image
     * @param factor Contrast adjustment [-1.0 to 1.0]
     *               -1.0 = no contrast (gray)
     *                0.0 = no change
     *                1.0 = maximum contrast
     * @return New image with adjusted contrast
     * @throws ValidationException if parameters are invalid
     */
    public static WritableImage adjustContrast(Image image, double factor) 
            throws ValidationException {
        
        Validator.requireNonNull(image, "image");
        Validator.validateAdjustment(factor,
            Constants.CONTRAST_MIN, Constants.CONTRAST_MAX, "contrast");
        
        long startTime = System.currentTimeMillis();
        Logger.debug(String.format("Adjusting contrast: factor=%.2f", factor));
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        double contrastFactor = (1.0 + factor);
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double r = PixelOperations.clamp((color.getRed() - 0.5) * contrastFactor + 0.5);
                double g = PixelOperations.clamp((color.getGreen() - 0.5) * contrastFactor + 0.5);
                double b = PixelOperations.clamp((color.getBlue() - 0.5) * contrastFactor + 0.5);
                writer.setColor(x, y, new Color(r, g, b, color.getOpacity()));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Contrast adjusted in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Adjust image saturation.
     * 
     * Algorithm: Interpolates between grayscale and original color
     * Complexity: O(width * height)
     * 
     * @param image Source image
     * @param factor Saturation adjustment [-1.0 to 1.0]
     *               -1.0 = grayscale
     *                0.0 = no change
     *                1.0 = highly saturated
     * @return New image with adjusted saturation
     * @throws ValidationException if parameters are invalid
     */
    public static WritableImage adjustSaturation(Image image, double factor) 
            throws ValidationException {
        
        Validator.requireNonNull(image, "image");
        Validator.validateAdjustment(factor,
            Constants.SATURATION_MIN, Constants.SATURATION_MAX, "saturation");
        
        long startTime = System.currentTimeMillis();
        Logger.debug(String.format("Adjusting saturation: factor=%.2f", factor));
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double gray = PixelOperations.toGrayscale(
                    color.getRed(), color.getGreen(), color.getBlue());
                
                double r = PixelOperations.clamp(gray + (color.getRed() - gray) * (1 + factor));
                double g = PixelOperations.clamp(gray + (color.getGreen() - gray) * (1 + factor));
                double b = PixelOperations.clamp(gray + (color.getBlue() - gray) * (1 + factor));
                
                writer.setColor(x, y, new Color(r, g, b, color.getOpacity()));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Saturation adjusted in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Apply Gaussian blur filter.
     * 
     * Algorithm: Separable Gaussian convolution
     * Complexity: O(width * height * radius)
     * 
     * @param image Source image
     * @param radius Blur radius [0 to 10]
     * @return Blurred image
     * @throws ValidationException if parameters are invalid
     */
    public static WritableImage gaussianBlur(Image image, int radius) 
            throws ValidationException {
        
        Validator.requireNonNull(image, "image");
        Validator.requireInRange(radius, 0, (int)Constants.BLUR_MAX, "blur radius");
        
        if (radius == 0) {
            return new WritableImage(image.getPixelReader(), 
                (int)image.getWidth(), (int)image.getHeight());
        }
        
        long startTime = System.currentTimeMillis();
        Logger.debug(String.format("Applying Gaussian blur: radius=%d", radius));
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        double[] kernel = createGaussianKernel(radius);
        int kernelSize = kernel.length;
        int halfSize = kernelSize / 2;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = 0, g = 0, b = 0, a = 0;
                
                for (int ky = -halfSize; ky <= halfSize; ky++) {
                    for (int kx = -halfSize; kx <= halfSize; kx++) {
                        int px = PixelOperations.clampInt(x + kx, 0, width - 1);
                        int py = PixelOperations.clampInt(y + ky, 0, height - 1);
                        
                        Color color = reader.getColor(px, py);
                        double weight = kernel[ky + halfSize] * kernel[kx + halfSize];
                        
                        r += color.getRed() * weight;
                        g += color.getGreen() * weight;
                        b += color.getBlue() * weight;
                        a += color.getOpacity() * weight;
                    }
                }
                writer.setColor(x, y, new Color(
                    PixelOperations.clamp(r), 
                    PixelOperations.clamp(g), 
                    PixelOperations.clamp(b), 
                    PixelOperations.clamp(a)
                ));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Gaussian blur applied in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Sharpen image using unsharp mask technique.
     * 
     * Algorithm: Laplacian kernel convolution
     * Complexity: O(width * height * 9)
     * 
     * @param image Source image
     * @param intensity Sharpening intensity [0.0 to 3.0]
     * @return Sharpened image
     * @throws ValidationException if parameters are invalid
     */
    public static WritableImage sharpen(Image image, double intensity) 
            throws ValidationException {
        
        Validator.requireNonNull(image, "image");
        Validator.validateAdjustment(intensity,
            Constants.SHARPEN_MIN, Constants.SHARPEN_MAX, "sharpen intensity");
        
        if (intensity == 0.0) {
            return new WritableImage(image.getPixelReader(),
                (int)image.getWidth(), (int)image.getHeight());
        }
        
        long startTime = System.currentTimeMillis();
        Logger.debug(String.format("Applying sharpen: intensity=%.2f", intensity));
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        // Laplacian kernel for edge detection
        double[][] kernel = {
            {0, -intensity, 0},
            {-intensity, 1 + 4 * intensity, -intensity},
            {0, -intensity, 0}
        };
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double r = 0, g = 0, b = 0;
                
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int px = PixelOperations.clampInt(x + kx, 0, width - 1);
                        int py = PixelOperations.clampInt(y + ky, 0, height - 1);
                        
                        Color color = reader.getColor(px, py);
                        double weight = kernel[ky + 1][kx + 1];
                        
                        r += color.getRed() * weight;
                        g += color.getGreen() * weight;
                        b += color.getBlue() * weight;
                    }
                }
                
                Color original = reader.getColor(x, y);
                writer.setColor(x, y, new Color(
                    PixelOperations.clamp(r),
                    PixelOperations.clamp(g),
                    PixelOperations.clamp(b),
                    original.getOpacity()
                ));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Sharpen applied in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Convert image to grayscale.
     * 
     * Algorithm: Luminosity method (ITU-R BT.709)
     * Complexity: O(width * height)
     * 
     * @param image Source image
     * @return Grayscale image
     * @throws ValidationException if image is null
     */
    public static WritableImage toGrayscale(Image image) throws ValidationException {
        Validator.requireNonNull(image, "image");
        
        long startTime = System.currentTimeMillis();
        Logger.debug("Converting to grayscale");
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double gray = PixelOperations.toGrayscale(
                    color.getRed(), color.getGreen(), color.getBlue());
                writer.setColor(x, y, new Color(gray, gray, gray, color.getOpacity()));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Grayscale conversion completed in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Apply sepia tone effect.
     * 
     * Algorithm: Standard sepia transformation matrix
     * Complexity: O(width * height)
     * 
     * @param image Source image
     * @return Sepia-toned image
     * @throws ValidationException if image is null
     */
    public static WritableImage sepiaTone(Image image) throws ValidationException {
        Validator.requireNonNull(image, "image");
        
        long startTime = System.currentTimeMillis();
        Logger.debug("Applying sepia tone");
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                double r = color.getRed();
                double g = color.getGreen();
                double b = color.getBlue();
                
                double tr = PixelOperations.clamp(0.393 * r + 0.769 * g + 0.189 * b);
                double tg = PixelOperations.clamp(0.349 * r + 0.686 * g + 0.168 * b);
                double tb = PixelOperations.clamp(0.272 * r + 0.534 * g + 0.131 * b);
                
                writer.setColor(x, y, new Color(tr, tg, tb, color.getOpacity()));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Sepia tone applied in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Invert all colors in the image.
     * 
     * Algorithm: Complement operation (1.0 - value)
     * Complexity: O(width * height)
     * 
     * @param image Source image
     * @return Inverted image
     * @throws ValidationException if image is null
     */
    public static WritableImage invert(Image image) throws ValidationException {
        Validator.requireNonNull(image, "image");
        
        long startTime = System.currentTimeMillis();
        Logger.debug("Inverting colors");
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                writer.setColor(x, y, new Color(
                    1.0 - color.getRed(),
                    1.0 - color.getGreen(),
                    1.0 - color.getBlue(),
                    color.getOpacity()
                ));
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Color inversion completed in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Rotate image by 90 degrees.
     * 
     * Complexity: O(width * height)
     * 
     * @param image Source image
     * @param clockwise true for clockwise, false for counter-clockwise
     * @return Rotated image
     * @throws ValidationException if image is null
     */
    public static WritableImage rotate90(Image image, boolean clockwise) 
            throws ValidationException {
        
        Validator.requireNonNull(image, "image");
        
        long startTime = System.currentTimeMillis();
        Logger.debug(String.format("Rotating 90° %s", clockwise ? "CW" : "CCW"));
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(height, width);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                if (clockwise) {
                    writer.setColor(height - 1 - y, x, color);
                } else {
                    writer.setColor(y, width - 1 - x, color);
                }
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Rotation completed in %dms", elapsed));
        
        return result;
    }
    
    /**
     * Flip image horizontally or vertically.
     * 
     * Complexity: O(width * height)
     * 
     * @param image Source image
     * @param horizontal true for horizontal flip, false for vertical
     * @return Flipped image
     * @throws ValidationException if image is null
     */
    public static WritableImage flip(Image image, boolean horizontal) 
            throws ValidationException {
        
        Validator.requireNonNull(image, "image");
        
        long startTime = System.currentTimeMillis();
        Logger.debug(String.format("Flipping %s", horizontal ? "horizontal" : "vertical"));
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage result = new WritableImage(width, height);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = result.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                if (horizontal) {
                    writer.setColor(width - 1 - x, y, color);
                } else {
                    writer.setColor(x, height - 1 - y, color);
                }
            }
        }
        
        long elapsed = System.currentTimeMillis() - startTime;
        Logger.info(String.format("Flip completed in %dms", elapsed));
        
        return result;
    }
    
    // ==================== Private Helper Methods ====================
    
    /**
     * Create 1D Gaussian kernel for blur operations.
     * 
     * @param radius Kernel radius
     * @return Normalized Gaussian kernel
     */
    private static double[] createGaussianKernel(int radius) {
        int size = radius * 2 + 1;
        double[] kernel = new double[size];
        double sigma = radius / 3.0;
        double sum = 0.0;
        
        for (int i = 0; i < size; i++) {
            int x = i - radius;
            kernel[i] = Math.exp(-(x * x) / (2 * sigma * sigma));
            sum += kernel[i];
        }
        
        // Normalize kernel
        for (int i = 0; i < size; i++) {
            kernel[i] /= sum;
        }
        
        return kernel;
    }
}
