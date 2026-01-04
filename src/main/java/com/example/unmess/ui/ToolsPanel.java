package com.example.unmess.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

// Photoshop-style vertical toolbar with tool selection
public class ToolsPanel extends VBox {
    
    private ToggleGroup toolGroup;
    private ToggleButton selectTool;
    private ToggleButton moveTool;
    private ToggleButton cropTool;
    private ToggleButton brushTool;
    private ToggleButton eraserTool;
    private ToggleButton textTool;
    private ToggleButton eyedropperTool;
    private ToggleButton zoomTool;
    
    public ToolsPanel() {
        super(5);
        setPadding(new Insets(10, 5, 10, 5));
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");
        setPrefWidth(60);
        
        toolGroup = new ToggleGroup();
        
        // Create tool buttons
        selectTool = createToolButton(IconLoader.getSelectionIcon(24), "Selection Tool (V)");
        moveTool = createToolButton(IconLoader.getMoveIcon(24), "Move Tool (M)");
        cropTool = createToolButton(IconLoader.getCropIcon(24), "Crop Tool (C)");
        brushTool = createToolButton(IconLoader.getBrushIcon(24), "Brush Tool (B)");
        eraserTool = createToolButton(IconLoader.getEraserIcon(24), "Eraser Tool (E)");
        textTool = createToolButton(IconLoader.getTextIcon(24), "Text Tool (T)");
        eyedropperTool = createToolButton(IconLoader.getEyedropperIcon(24), "Eyedropper Tool (I)");
        zoomTool = createToolButton(IconLoader.getZoomToolIcon(24), "Zoom Tool (Z)");
        
        // Select move tool by default
        moveTool.setSelected(true);
        
        getChildren().addAll(
            selectTool,
            moveTool,
            new Separator(Orientation.HORIZONTAL),
            cropTool,
            new Separator(Orientation.HORIZONTAL),
            brushTool,
            eraserTool,
            new Separator(Orientation.HORIZONTAL),
            textTool,
            eyedropperTool,
            new Separator(Orientation.HORIZONTAL),
            zoomTool
        );
    }
    
    private ToggleButton createToolButton(javafx.scene.Node icon, String tooltip) {
        ToggleButton btn = new ToggleButton();
        btn.setGraphic(icon);
        btn.setToggleGroup(toolGroup);
        btn.setTooltip(new Tooltip(tooltip));
        btn.setPrefSize(50, 50);
        btn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-background-radius: 4;"
        );
        
        btn.setOnMouseEntered(e -> {
            if (!btn.isSelected()) {
                btn.setStyle(
                    "-fx-background-color: #f0f0f0; " +
                    "-fx-background-radius: 4;"
                );
            }
        });
        
        btn.setOnMouseExited(e -> {
            if (!btn.isSelected()) {
                btn.setStyle(
                    "-fx-background-color: transparent; " +
                    "-fx-background-radius: 4;"
                );
            }
        });
        
        btn.selectedProperty().addListener((obs, old, selected) -> {
            if (selected) {
                btn.setStyle(
                    "-fx-background-color: #2196F3; " +
                    "-fx-background-radius: 4;"
                );
            } else {
                btn.setStyle(
                    "-fx-background-color: transparent; " +
                    "-fx-background-radius: 4;"
                );
            }
        });
        
        return btn;
    }
    
    public String getSelectedTool() {
        ToggleButton selected = (ToggleButton) toolGroup.getSelectedToggle();
        return selected != null ? selected.getTooltip().getText() : "Move Tool";
    }
}
