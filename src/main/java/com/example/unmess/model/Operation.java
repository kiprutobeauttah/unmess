package com.example.unmess.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable record of an image processing operation.
 * Enables operation tracking and potential replay functionality.
 * 
 * @author Photo Editor Engineering Team
 * @version 1.0.0
 */
public final class Operation {
    
    public enum Type {
        BRIGHTNESS, CONTRAST, SATURATION, BLUR, SHARPEN,
        GRAYSCALE, SEPIA, INVERT,
        ROTATE_CW, ROTATE_CCW, FLIP_H, FLIP_V,
        CROP, RESIZE
    }
    
    private final Type type;
    private final Map<String, Object> parameters;
    private final LocalDateTime timestamp;
    private final long executionTimeMs;
    
    private Operation(Type type, Map<String, Object> parameters, 
                     LocalDateTime timestamp, long executionTimeMs) {
        this.type = Objects.requireNonNull(type, "Operation type cannot be null");
        this.parameters = Collections.unmodifiableMap(new HashMap<>(parameters));
        this.timestamp = timestamp;
        this.executionTimeMs = executionTimeMs;
    }
    
    public Type getType() { return type; }
    public Map<String, Object> getParameters() { return parameters; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public long getExecutionTimeMs() { return executionTimeMs; }
    
    public Object getParameter(String key) {
        return parameters.get(key);
    }
    
    public double getDoubleParameter(String key, double defaultValue) {
        Object value = parameters.get(key);
        return value instanceof Number ? ((Number) value).doubleValue() : defaultValue;
    }
    
    @Override
    public String toString() {
        return String.format("Operation[%s, params=%s, time=%dms]",
            type, parameters, executionTimeMs);
    }
    
    // Builder Pattern
    public static class Builder {
        private Type type;
        private Map<String, Object> parameters = new HashMap<>();
        private LocalDateTime timestamp = LocalDateTime.now();
        private long executionTimeMs = 0;
        
        public Builder type(Type type) {
            this.type = type;
            return this;
        }
        
        public Builder parameter(String key, Object value) {
            this.parameters.put(key, value);
            return this;
        }
        
        public Builder parameters(Map<String, Object> parameters) {
            this.parameters.putAll(parameters);
            return this;
        }
        
        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public Builder executionTimeMs(long executionTimeMs) {
            this.executionTimeMs = executionTimeMs;
            return this;
        }
        
        public Operation build() {
            return new Operation(type, parameters, timestamp, executionTimeMs);
        }
    }
}
