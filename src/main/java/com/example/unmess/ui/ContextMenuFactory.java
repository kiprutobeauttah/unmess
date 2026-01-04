package com.example.unmess.ui;

import javafx.scene.control.*;
import java.util.function.Consumer;

// Factory for creating context menus
public class ContextMenuFactory {
    
    public static ContextMenu createImageContextMenu(Consumer<String> actionHandler) {
        ContextMenu menu = new ContextMenu();
        
        MenuItem cutItem = new MenuItem("Cut");
        cutItem.setOnAction(e -> actionHandler.accept("cut"));
        
        MenuItem copyItem = new MenuItem("Copy");
        copyItem.setOnAction(e -> actionHandler.accept("copy"));
        
        MenuItem pasteItem = new MenuItem("Paste");
        pasteItem.setOnAction(e -> actionHandler.accept("paste"));
        
        Menu transformMenu = new Menu("Transform");
        MenuItem rotateCWItem = new MenuItem("Rotate 90° CW");
        rotateCWItem.setOnAction(e -> actionHandler.accept("rotateCW"));
        MenuItem rotateCCWItem = new MenuItem("Rotate 90° CCW");
        rotateCCWItem.setOnAction(e -> actionHandler.accept("rotateCCW"));
        MenuItem flipHItem = new MenuItem("Flip Horizontal");
        flipHItem.setOnAction(e -> actionHandler.accept("flipH"));
        MenuItem flipVItem = new MenuItem("Flip Vertical");
        flipVItem.setOnAction(e -> actionHandler.accept("flipV"));
        transformMenu.getItems().addAll(rotateCWItem, rotateCCWItem, new SeparatorMenuItem(), flipHItem, flipVItem);
        
        Menu filterMenu = new Menu("Apply Filter");
        MenuItem grayscaleItem = new MenuItem("Grayscale");
        grayscaleItem.setOnAction(e -> actionHandler.accept("grayscale"));
        MenuItem sepiaItem = new MenuItem("Sepia Tone");
        sepiaItem.setOnAction(e -> actionHandler.accept("sepia"));
        MenuItem invertItem = new MenuItem("Invert Colors");
        invertItem.setOnAction(e -> actionHandler.accept("invert"));
        filterMenu.getItems().addAll(grayscaleItem, sepiaItem, invertItem);
        
        menu.getItems().addAll(cutItem, copyItem, pasteItem, new SeparatorMenuItem(), 
            transformMenu, filterMenu);
        
        return menu;
    }
    
    public static ContextMenu createLayerContextMenu(Consumer<String> actionHandler) {
        ContextMenu menu = new ContextMenu();
        
        MenuItem newLayerItem = new MenuItem("New Layer");
        newLayerItem.setOnAction(e -> actionHandler.accept("newLayer"));
        
        MenuItem duplicateLayerItem = new MenuItem("Duplicate Layer");
        duplicateLayerItem.setOnAction(e -> actionHandler.accept("duplicateLayer"));
        
        MenuItem deleteLayerItem = new MenuItem("Delete Layer");
        deleteLayerItem.setOnAction(e -> actionHandler.accept("deleteLayer"));
        
        menu.getItems().addAll(newLayerItem, duplicateLayerItem, deleteLayerItem);
        
        return menu;
    }
    
    public static ContextMenu createHistoryContextMenu(Consumer<String> actionHandler) {
        ContextMenu menu = new ContextMenu();
        
        MenuItem revertItem = new MenuItem("Revert to This State");
        revertItem.setOnAction(e -> actionHandler.accept("revert"));
        
        MenuItem clearHistoryItem = new MenuItem("Clear All History");
        clearHistoryItem.setOnAction(e -> actionHandler.accept("clearAll"));
        
        menu.getItems().addAll(revertItem, new SeparatorMenuItem(), clearHistoryItem);
        
        return menu;
    }
}
