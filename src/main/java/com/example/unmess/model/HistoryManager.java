package com.example.unmess.model;

import com.example.unmess.core.Constants;
import com.example.unmess.core.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Thread-safe history manager implementing undo/redo functionality.
 * Uses bounded deque for memory-efficient state management.
 * 
 * Design Pattern: Memento Pattern
 * Thread Safety: Synchronized methods for concurrent access
 * Memory Management: Bounded history with automatic cleanup
 * 
 * @author Photo Editor Engineering Team
 * @version 2.0.0
 */
public class HistoryManager {
    
    private final Deque<ImageState> undoStack;
    private final Deque<ImageState> redoStack;
    private final int maxHistorySize;
    private long totalMemoryUsed;
    
    /**
     * Create history manager with default capacity
     */
    public HistoryManager() {
        this(Constants.MAX_HISTORY_SIZE);
    }
    
    /**
     * Create history manager with specified capacity
     * 
     * @param maxHistorySize Maximum number of states to retain
     * @throws IllegalArgumentException if maxHistorySize <= 0
     */
    public HistoryManager(int maxHistorySize) {
        if (maxHistorySize <= 0) {
            throw new IllegalArgumentException("Max history size must be positive");
        }
        
        this.maxHistorySize = maxHistorySize;
        this.undoStack = new ArrayDeque<>(maxHistorySize);
        this.redoStack = new ArrayDeque<>(maxHistorySize);
        this.totalMemoryUsed = 0;
        
        Logger.info("HistoryManager initialized with capacity: " + maxHistorySize);
    }
    
    /**
     * Save current state to history
     * 
     * @param state Current image state to save
     * @throws NullPointerException if state is null
     */
    public synchronized void saveState(ImageState state) {
        Objects.requireNonNull(state, "State cannot be null");
        
        // Remove oldest state if at capacity
        if (undoStack.size() >= maxHistorySize) {
            ImageState removed = undoStack.removeFirst();
            updateMemoryUsage(removed, false);
            Logger.debug("Removed oldest state from history (capacity reached)");
        }
        
        undoStack.addLast(state);
        updateMemoryUsage(state, true);
        
        // Clear redo stack when new state is saved
        if (!redoStack.isEmpty()) {
            redoStack.forEach(s -> updateMemoryUsage(s, false));
            redoStack.clear();
            Logger.debug("Cleared redo stack");
        }
        
        Logger.debug(String.format("State saved. History size: %d, Memory: %.2f MB",
            undoStack.size(), totalMemoryUsed / (1024.0 * 1024.0)));
    }
    
    /**
     * Undo last operation
     * 
     * @return Previous state, or null if nothing to undo
     */
    public synchronized ImageState undo() {
        if (undoStack.isEmpty()) {
            Logger.debug("Undo requested but history is empty");
            return null;
        }
        
        ImageState currentState = undoStack.removeLast();
        redoStack.addLast(currentState);
        
        ImageState previousState = undoStack.isEmpty() ? null : undoStack.peekLast();
        
        Logger.info(String.format("Undo performed. Undo stack: %d, Redo stack: %d",
            undoStack.size(), redoStack.size()));
        
        return previousState;
    }
    
    /**
     * Redo last undone operation
     * 
     * @return Next state, or null if nothing to redo
     */
    public synchronized ImageState redo() {
        if (redoStack.isEmpty()) {
            Logger.debug("Redo requested but redo stack is empty");
            return null;
        }
        
        ImageState nextState = redoStack.removeLast();
        undoStack.addLast(nextState);
        
        Logger.info(String.format("Redo performed. Undo stack: %d, Redo stack: %d",
            undoStack.size(), redoStack.size()));
        
        return nextState;
    }
    
    /**
     * Check if undo is available
     */
    public synchronized boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    /**
     * Check if redo is available
     */
    public synchronized boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    /**
     * Get number of available undo operations
     */
    public synchronized int getUndoCount() {
        return undoStack.size();
    }
    
    /**
     * Get number of available redo operations
     */
    public synchronized int getRedoCount() {
        return redoStack.size();
    }
    
    /**
     * Get total memory used by history (approximate)
     */
    public synchronized long getTotalMemoryUsed() {
        return totalMemoryUsed;
    }
    
    /**
     * Clear all history
     */
    public synchronized void clear() {
        int undoSize = undoStack.size();
        int redoSize = redoStack.size();
        
        undoStack.clear();
        redoStack.clear();
        totalMemoryUsed = 0;
        
        Logger.info(String.format("History cleared. Removed %d undo and %d redo states",
            undoSize, redoSize));
    }
    
    /**
     * Update memory usage tracking
     */
    private void updateMemoryUsage(ImageState state, boolean add) {
        long stateSize = state.getMetadata().getSizeBytes();
        totalMemoryUsed += add ? stateSize : -stateSize;
    }
    
    /**
     * Get current history statistics
     */
    public synchronized String getStatistics() {
        return String.format(
            "History[undo=%d, redo=%d, memory=%.2f MB, capacity=%d]",
            undoStack.size(), redoStack.size(),
            totalMemoryUsed / (1024.0 * 1024.0), maxHistorySize
        );
    }
    
    @Override
    public synchronized String toString() {
        return getStatistics();
    }
}
