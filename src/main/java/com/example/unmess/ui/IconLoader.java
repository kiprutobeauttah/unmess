package com.example.unmess.ui;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

// Utility class for loading icons using Ikonli Feather icons
public class IconLoader {
    
    private static final Color ICON_COLOR = Color.web("#cccccc");
    
    // File operations
    public static Node getNewIcon(double size) {
        return createIcon(Feather.FILE_PLUS, size);
    }
    
    public static Node getOpenIcon(double size) {
        return createIcon(Feather.FOLDER, size);
    }
    
    public static Node getSaveIcon(double size) {
        return createIcon(Feather.SAVE, size);
    }
    
    // Edit operations
    public static Node getUndoIcon(double size) {
        return createIcon(Feather.CORNER_UP_LEFT, size);
    }
    
    public static Node getRedoIcon(double size) {
        return createIcon(Feather.CORNER_UP_RIGHT, size);
    }
    
    public static Node getCutIcon(double size) {
        return createIcon(Feather.SCISSORS, size);
    }
    
    public static Node getCopyIcon(double size) {
        return createIcon(Feather.COPY, size);
    }
    
    public static Node getPasteIcon(double size) {
        return createIcon(Feather.CLIPBOARD, size);
    }
    
    // View operations
    public static Node getZoomInIcon(double size) {
        return createIcon(Feather.ZOOM_IN, size);
    }
    
    public static Node getZoomOutIcon(double size) {
        return createIcon(Feather.ZOOM_OUT, size);
    }
    
    public static Node getFitIcon(double size) {
        return createIcon(Feather.MAXIMIZE, size);
    }
    
    // Tools
    public static Node getSelectionIcon(double size) {
        return createIcon(Feather.SQUARE, size);
    }
    
    public static Node getMoveIcon(double size) {
        return createIcon(Feather.MOVE, size);
    }
    
    public static Node getCropIcon(double size) {
        return createIcon(Feather.CROP, size);
    }
    
    public static Node getBrushIcon(double size) {
        return createIcon(Feather.EDIT_3, size);
    }
    
    public static Node getEraserIcon(double size) {
        return createIcon(Feather.DELETE, size);
    }
    
    public static Node getTextIcon(double size) {
        return createIcon(Feather.TYPE, size);
    }
    
    public static Node getEyedropperIcon(double size) {
        return createIcon(Feather.DROPLET, size);
    }
    
    public static Node getZoomToolIcon(double size) {
        return createIcon(Feather.ZOOM_IN, size);
    }
    
    // Panel icons
    public static Node getAdjustmentsIcon(double size) {
        return createIcon(Feather.SLIDERS, size);
    }
    
    public static Node getLayersIcon(double size) {
        return createIcon(Feather.LAYERS, size);
    }
    
    public static Node getHistoryIcon(double size) {
        return createIcon(Feather.CLOCK, size);
    }
    
    public static Node getPropertiesIcon(double size) {
        return createIcon(Feather.LIST, size);
    }
    
    public static Node getFilterIcon(double size) {
        return createIcon(Feather.FILTER, size);
    }
    
    // Helper method to create FontIcon
    private static FontIcon createIcon(Feather icon, double size) {
        FontIcon fontIcon = new FontIcon(icon);
        fontIcon.setIconSize((int) size);
        fontIcon.setIconColor(ICON_COLOR);
        return fontIcon;
    }
    
    // Load icon with custom path (deprecated - returns default icon)
    public static Node loadCustomIcon(String path, double size) {
        return createIcon(Feather.FILE, size);
    }
}
