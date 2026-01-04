package com.example.unmess.ui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.Consumer;

// Professional top toolbar with icon buttons
public class TopToolBar extends HBox {
    
    private Consumer<String> actionHandler;
    
    public TopToolBar() {
        super(5);
        setPadding(new Insets(8, 10, 8, 10));
        setAlignment(Pos.CENTER_LEFT);
        setStyle("-fx-background-color: #ffffff; " +
                 "-fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        
        // Title
        Label brandLabel = new Label("UNMESS");
        brandLabel.setStyle(
            "-fx-text-fill: #2196F3; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 16px; " +
            "-fx-padding: 0 20 0 5;"
        );
        
        Separator sep0 = new Separator(Orientation.VERTICAL);
        sep0.setPadding(new Insets(0, 5, 0, 5));
        
        // File operations group
        HBox fileGroup = new HBox(3);
        fileGroup.setAlignment(Pos.CENTER_LEFT);
        Button newBtn = createIconButton(IconLoader.getNewIcon(20), "New");
        newBtn.setOnAction(e -> handleAction("new"));
        Button openBtn = createIconButton(IconLoader.getOpenIcon(20), "Open");
        openBtn.setOnAction(e -> handleAction("open"));
        Button saveBtn = createIconButton(IconLoader.getSaveIcon(20), "Save");
        saveBtn.setOnAction(e -> handleAction("save"));
        fileGroup.getChildren().addAll(newBtn, openBtn, saveBtn);
        
        Separator sep1 = new Separator(Orientation.VERTICAL);
        sep1.setPadding(new Insets(0, 5, 0, 5));
        
        // Edit operations group
        HBox editGroup = new HBox(3);
        editGroup.setAlignment(Pos.CENTER_LEFT);
        Button undoBtn = createIconButton(IconLoader.getUndoIcon(20), "Undo");
        undoBtn.setOnAction(e -> handleAction("undo"));
        Button redoBtn = createIconButton(IconLoader.getRedoIcon(20), "Redo");
        redoBtn.setOnAction(e -> handleAction("redo"));
        Button cutBtn = createIconButton(IconLoader.getCutIcon(20), "Cut");
        cutBtn.setOnAction(e -> handleAction("cut"));
        Button copyBtn = createIconButton(IconLoader.getCopyIcon(20), "Copy");
        copyBtn.setOnAction(e -> handleAction("copy"));
        Button pasteBtn = createIconButton(IconLoader.getPasteIcon(20), "Paste");
        pasteBtn.setOnAction(e -> handleAction("paste"));
        editGroup.getChildren().addAll(undoBtn, redoBtn, cutBtn, copyBtn, pasteBtn);
        
        Separator sep2 = new Separator(Orientation.VERTICAL);
        sep2.setPadding(new Insets(0, 5, 0, 5));
        
        // View operations group
        HBox viewGroup = new HBox(3);
        viewGroup.setAlignment(Pos.CENTER_LEFT);
        Button zoomInBtn = createIconButton(IconLoader.getZoomInIcon(20), "Zoom In");
        zoomInBtn.setOnAction(e -> handleAction("zoomIn"));
        Button zoomOutBtn = createIconButton(IconLoader.getZoomOutIcon(20), "Zoom Out");
        zoomOutBtn.setOnAction(e -> handleAction("zoomOut"));
        Button fitBtn = createIconButton(IconLoader.getFitIcon(20), "Fit");
        fitBtn.setOnAction(e -> handleAction("fit"));
        viewGroup.getChildren().addAll(zoomInBtn, zoomOutBtn, fitBtn);
        
        Separator sep3 = new Separator(Orientation.VERTICAL);
        sep3.setPadding(new Insets(0, 5, 0, 5));
        
        // Quick tools group
        HBox toolsGroup = new HBox(3);
        toolsGroup.setAlignment(Pos.CENTER_LEFT);
        Button cropBtn = createIconButton(IconLoader.getCropIcon(20), "Crop");
        cropBtn.setOnAction(e -> handleAction("crop"));
        Button brushBtn = createIconButton(IconLoader.getBrushIcon(20), "Brush");
        brushBtn.setOnAction(e -> handleAction("brush"));
        Button textBtn = createIconButton(IconLoader.getTextIcon(20), "Text");
        textBtn.setOnAction(e -> handleAction("text"));
        toolsGroup.getChildren().addAll(cropBtn, brushBtn, textBtn);
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Right side - quick access
        HBox rightGroup = new HBox(8);
        rightGroup.setAlignment(Pos.CENTER_RIGHT);
        
        // Zoom percentage display
        Label zoomLabel = new Label("100%");
        zoomLabel.setStyle(
            "-fx-text-fill: #2196F3; " +
            "-fx-font-family: 'Consolas', monospace; " +
            "-fx-font-size: 13px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 6 12 6 12; " +
            "-fx-background-color: #f0f0f0; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        );
        
        rightGroup.getChildren().addAll(zoomLabel);
        
        getChildren().addAll(
            brandLabel,
            sep0,
            fileGroup,
            sep1,
            editGroup,
            sep2,
            viewGroup,
            sep3,
            toolsGroup,
            spacer,
            rightGroup
        );
    }
    
    private Button createIconButton(javafx.scene.Node icon, String tooltip) {
        Button btn = new Button();
        btn.setGraphic(icon);
        btn.setTooltip(new Tooltip(tooltip));
        btn.setPrefSize(36, 36);
        btn.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-background-radius: 4; " +
            "-fx-padding: 6; " +
            "-fx-cursor: hand; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        );
        
        // Hover effect
        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                "-fx-background-color: #2196F3; " +
                "-fx-background-radius: 4; " +
                "-fx-padding: 6; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: #2196F3; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 4;"
            );
        });
        
        btn.setOnMouseExited(e -> {
            btn.setStyle(
                "-fx-background-color: #f5f5f5; " +
                "-fx-background-radius: 4; " +
                "-fx-padding: 6; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: #e0e0e0; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 4;"
            );
        });
        
        btn.setOnMousePressed(e -> {
            btn.setStyle(
                "-fx-background-color: #1976D2; " +
                "-fx-background-radius: 4; " +
                "-fx-padding: 6; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: #1976D2; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 4;"
            );
        });
        
        return btn;
    }
    
    private void handleAction(String action) {
        if (actionHandler != null) {
            actionHandler.accept(action);
        }
    }
    
    public void setActionHandler(Consumer<String> handler) {
        this.actionHandler = handler;
    }
    
    public void updateZoom(double zoom) {
        // Find and update zoom label
        getChildren().stream()
            .filter(node -> node instanceof HBox)
            .flatMap(node -> ((HBox)node).getChildren().stream())
            .filter(node -> node instanceof Label)
            .map(node -> (Label)node)
            .filter(label -> label.getText().contains("%"))
            .findFirst()
            .ifPresent(label -> label.setText(String.format("%.0f%%", zoom * 100)));
    }
}
