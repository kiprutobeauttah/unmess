package com.example.unmess.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// Photoshop-style layers panel
public class LayersPanel extends VBox {
    
    private ListView<String> layersList;
    private Slider opacitySlider;
    private ComboBox<String> blendModeCombo;
    
    public LayersPanel() {
        super(10);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #ffffff;");
        
        // Title
        Label titleLabel = new Label("LAYERS");
        titleLabel.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 11px;");
        
        // Blend mode
        HBox blendBox = new HBox(5);
        blendBox.setAlignment(Pos.CENTER_LEFT);
        Label blendLabel = new Label("Mode:");
        blendLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 11px;");
        blendModeCombo = new ComboBox<>();
        blendModeCombo.getItems().addAll(
            "Normal", "Multiply", "Screen", "Overlay", "Soft Light", 
            "Hard Light", "Darken", "Lighten", "Color Dodge", "Color Burn"
        );
        blendModeCombo.setValue("Normal");
        blendModeCombo.setPrefWidth(150);
        blendBox.getChildren().addAll(blendLabel, blendModeCombo);
        
        // Opacity
        HBox opacityBox = new HBox(5);
        opacityBox.setAlignment(Pos.CENTER_LEFT);
        Label opacityLabel = new Label("Opacity:");
        opacityLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 11px;");
        opacitySlider = new Slider(0, 100, 100);
        opacitySlider.setPrefWidth(120);
        Label opacityValue = new Label("100%");
        opacityValue.setStyle("-fx-text-fill: #2196F3; -fx-font-size: 11px; -fx-font-family: monospace;");
        opacitySlider.valueProperty().addListener((obs, old, val) -> 
            opacityValue.setText(String.format("%d%%", val.intValue()))
        );
        opacityBox.getChildren().addAll(opacityLabel, opacitySlider, opacityValue);
        
        // Layers list
        layersList = new ListView<>();
        layersList.getItems().addAll("Background", "Layer 1");
        layersList.getSelectionModel().select(0);
        layersList.setPrefHeight(200);
        layersList.setStyle(
            "-fx-background-color: #1e1e1e; " +
            "-fx-border-color: #3e3e42; " +
            "-fx-border-width: 1;"
        );
        
        // Add context menu to layers list
        ContextMenu layerContextMenu = ContextMenuFactory.createLayerContextMenu(action -> {
            System.out.println("Layer action: " + action);
        });
        layersList.setContextMenu(layerContextMenu);
        
        // Layer controls
        HBox layerControls = new HBox(5);
        layerControls.setAlignment(Pos.CENTER);
        Button newLayerBtn = createIconButton(IconLoader.getNewIcon(16), "New Layer");
        Button deleteLayerBtn = createIconButton(IconLoader.getEraserIcon(16), "Delete Layer");
        Button duplicateLayerBtn = createIconButton(IconLoader.getCopyIcon(16), "Duplicate Layer");
        Button mergeLayerBtn = createIconButton(IconLoader.getLayersIcon(16), "Merge Down");
        layerControls.getChildren().addAll(newLayerBtn, deleteLayerBtn, duplicateLayerBtn, mergeLayerBtn);
        
        getChildren().addAll(
            titleLabel,
            blendBox,
            opacityBox,
            new Separator(),
            layersList,
            layerControls
        );
    }
    
    private Button createIconButton(javafx.scene.Node icon, String tooltip) {
        Button btn = new Button();
        btn.setGraphic(icon);
        btn.setTooltip(new Tooltip(tooltip));
        btn.setPrefSize(35, 30);
        btn.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #2196F3; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #2196F3; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        ));
        return btn;
    }
}
