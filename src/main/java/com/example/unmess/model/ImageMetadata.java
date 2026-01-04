package com.example.unmess.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Immutable metadata container for image information.
 * Tracks image properties and modification history.
 * 
 * @author Photo Editor Engineering Team
 * @version 1.0.0
 */
public final class ImageMetadata {
    
    private final int width;
    private final int height;
    private final String format;
    private final long sizeBytes;
    private final LocalDateTime loadedAt;
    private final LocalDateTime lastModified;
    private final String sourcePath;
    private final int modificationCount;
    
    private ImageMetadata(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.format = builder.format;
        this.sizeBytes = builder.sizeBytes;
        this.loadedAt = builder.loadedAt;
        this.lastModified = builder.lastModified;
        this.sourcePath = builder.sourcePath;
        this.modificationCount = builder.modificationCount;
    }
    
    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getFormat() { return format; }
    public long getSizeBytes() { return sizeBytes; }
    public LocalDateTime getLoadedAt() { return loadedAt; }
    public LocalDateTime getLastModified() { return lastModified; }
    public String getSourcePath() { return sourcePath; }
    public int getModificationCount() { return modificationCount; }
    
    /**
     * Create a new metadata instance with incremented modification count
     */
    public ImageMetadata withModification() {
        return new Builder()
            .width(width)
            .height(height)
            .format(format)
            .sizeBytes(sizeBytes)
            .loadedAt(loadedAt)
            .lastModified(LocalDateTime.now())
            .sourcePath(sourcePath)
            .modificationCount(modificationCount + 1)
            .build();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageMetadata that = (ImageMetadata) o;
        return width == that.width && height == that.height && 
               Objects.equals(sourcePath, that.sourcePath);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(width, height, sourcePath);
    }
    
    @Override
    public String toString() {
        return String.format("ImageMetadata[%dx%d, %s, %d bytes, mods=%d]",
            width, height, format, sizeBytes, modificationCount);
    }
    
    // Builder Pattern
    public static class Builder {
        private int width;
        private int height;
        private String format = "unknown";
        private long sizeBytes;
        private LocalDateTime loadedAt = LocalDateTime.now();
        private LocalDateTime lastModified = LocalDateTime.now();
        private String sourcePath = "";
        private int modificationCount = 0;
        
        public Builder width(int width) {
            this.width = width;
            return this;
        }
        
        public Builder height(int height) {
            this.height = height;
            return this;
        }
        
        public Builder format(String format) {
            this.format = format;
            return this;
        }
        
        public Builder sizeBytes(long sizeBytes) {
            this.sizeBytes = sizeBytes;
            return this;
        }
        
        public Builder loadedAt(LocalDateTime loadedAt) {
            this.loadedAt = loadedAt;
            return this;
        }
        
        public Builder lastModified(LocalDateTime lastModified) {
            this.lastModified = lastModified;
            return this;
        }
        
        public Builder sourcePath(String sourcePath) {
            this.sourcePath = sourcePath;
            return this;
        }
        
        public Builder modificationCount(int modificationCount) {
            this.modificationCount = modificationCount;
            return this;
        }
        
        public ImageMetadata build() {
            return new ImageMetadata(this);
        }
    }
}
