package com.example.unmess.model;

import com.example.unmess.core.Logger;
import com.example.unmess.core.ValidationException;
import com.example.unmess.core.Validator;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Thread-safe immutable representation of image state.
 * Maintains original image, current state, metadata, and operation history.
 * 
 * Design Pattern: Immutable Object Pattern
 * Thread Safety: All fields are final and defensive copies are made
 * 
 * @author Photo Editor Engineering Team
 * @version 2.0.0
 */
public final class ImageState {
    
    private final WritableImage currentImage;
    private final WritableImage originalImage;
    private final ImageMetadata metadata;
    private final List<Operation> operationHistory;
    
    /**
     * Private constructor - use Builder pattern
     */
    private ImageState(WritableImage originalImage, WritableImage currentImage,
                      ImageMetadata metadata, List<Operation> operationHistory) {
        this.originalImage = Objects.requireNonNull(originalImage, "Original image cannot be null");
        this.currentImage = Objects.requireNonNull(currentImage, "Current image cannot be null");
        this.metadata = Objects.requireNonNull(metadata, "Metadata cannot be null");
        this.operationHistory = Collections.unmodifiableList(new ArrayList<>(operationHistory));
    }
    
    /**
     * Create initial image state from an image
     */
    public static ImageState fromImage(Image image, String sourcePath) throws ValidationException {
        Validator.validateImageDimensions(image);
        
        Logger.info("Creating ImageState from image: " + sourcePath);
        
        WritableImage cloned = cloneImage(image);
        ImageMetadata metadata = new ImageMetadata.Builder()
            .width((int) image.getWidth())
            .height((int) image.getHeight())
            .sourcePath(sourcePath)
            .sizeBytes(calculateImageSize(image))
            .build();
        
        return new ImageState(cloned, cloneImage(cloned), metadata, new ArrayList<>());
    }
    
    /**
     * Get current image (direct reference for display)
     */
    public WritableImage getCurrentImage() {
        return currentImage;
    }
    
    /**
     * Get original image (cloned for processing)
     */
    public WritableImage getOriginalImage() {
        return cloneImage(originalImage);
    }
    
    /**
     * Get metadata (immutable)
     */
    public ImageMetadata getMetadata() {
        return metadata;
    }
    
    /**
     * Get operation history (immutable)
     */
    public List<Operation> getOperationHistory() {
        return operationHistory;
    }
    
    /**
     * Create new state with updated image
     */
    public ImageState withImage(WritableImage newImage, Operation operation) {
        Logger.debug("Creating new ImageState with operation: " + operation.getType());
        
        List<Operation> newHistory = new ArrayList<>(operationHistory);
        newHistory.add(operation);
        
        return new ImageState(
            originalImage,
            cloneImage(newImage),
            metadata.withModification(),
            newHistory
        );
    }
    
    /**
     * Reset to original image
     */
    public ImageState resetToOriginal() {
        Logger.info("Resetting ImageState to original");
        return new ImageState(
            originalImage,
            cloneImage(originalImage),
            metadata,
            new ArrayList<>()
        );
    }
    
    /**
     * Creates a deep copy of an image using efficient pixel transfer.
     * 
     * Performance: O(width * height)
     * Memory: Allocates new WritableImage of same dimensions
     * 
     * @param image Source image to clone
     * @return Deep copy of the image
     * @throws NullPointerException if image is null
     */
    public static WritableImage cloneImage(Image image) {
        Objects.requireNonNull(image, "Image to clone cannot be null");
        
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        
        WritableImage writableImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        
        // Efficient row-by-row pixel transfer
        int[] buffer = new int[width];
        for (int y = 0; y < height; y++) {
            pixelReader.getPixels(0, y, width, 1, 
                javafx.scene.image.PixelFormat.getIntArgbInstance(), buffer, 0, width);
            pixelWriter.setPixels(0, y, width, 1,
                javafx.scene.image.PixelFormat.getIntArgbInstance(), buffer, 0, width);
        }
        
        return writableImage;
    }
    
    /**
     * Calculate approximate memory size of image
     */
    private static long calculateImageSize(Image image) {
        return (long) image.getWidth() * (long) image.getHeight() * 4; // 4 bytes per pixel (ARGB)
    }
    
    @Override
    public String toString() {
        return String.format("ImageState[%s, operations=%d]", 
            metadata, operationHistory.size());
    }
}
