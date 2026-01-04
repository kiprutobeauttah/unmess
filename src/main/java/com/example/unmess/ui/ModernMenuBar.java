package com.example.unmess.ui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

// Modern flat menubar with excellent contrast
public class ModernMenuBar extends HBox {
    
    private Consumer<String> actionHandler;
    
    public ModernMenuBar(Stage stage) {
        super(0);
        setAlignment(Pos.CENTER_LEFT);
        setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 0 0 1 0; " +
            "-fx-padding: 0;"
        );
        
        // App title/logo
        Label appTitle = new Label("UNMESS");
        appTitle.setStyle(
            "-fx-text-fill: #2196F3; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12 20 12 20; " +
            "-fx-cursor: hand;"
        );
        
        // Menu buttons
        Button fileBtn = createMenuButton("File");
        Button editBtn = createMenuButton("Edit");
        Button imageBtn = createMenuButton("Image");
        Button filterBtn = createMenuButton("Filter");
        Button viewBtn = createMenuButton("View");
        Button helpBtn = createMenuButton("Help");
        
        // Setup menus
        setupFileMenu(fileBtn, stage);
        setupEditMenu(editBtn);
        setupImageMenu(imageBtn);
        setupFilterMenu(filterBtn);
        setupViewMenu(viewBtn);
        setupHelpMenu(helpBtn);
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Right side info
        Label versionLabel = new Label("v1.0");
        versionLabel.setStyle(
            "-fx-text-fill: #999999; " +
            "-fx-font-size: 11px; " +
            "-fx-padding: 12 20 12 10;"
        );
        
        getChildren().addAll(
            appTitle,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            fileBtn, editBtn, imageBtn, filterBtn, viewBtn, helpBtn,
            spacer,
            versionLabel
        );
    }
    
    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #333333; " +
            "-fx-font-size: 13px; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 12 16 12 16; " +
            "-fx-cursor: hand; " +
            "-fx-background-radius: 0;"
        );
        
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-text-fill: #2196F3; " +
            "-fx-font-size: 13px; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 12 16 12 16; " +
            "-fx-cursor: hand; " +
            "-fx-background-radius: 0;"
        ));
        
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #333333; " +
            "-fx-font-size: 13px; " +
            "-fx-font-weight: 500; " +
            "-fx-padding: 12 16 12 16; " +
            "-fx-cursor: hand; " +
            "-fx-background-radius: 0;"
        ));
        
        return btn;
    }
    
    private void setupFileMenu(Button btn, Stage stage) {
        ContextMenu menu = new ContextMenu();
        menu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        
        MenuItem newItem = createMenuItem("New", "Ctrl+N");
        MenuItem openItem = createMenuItem("Open...", "Ctrl+O");
        openItem.setOnAction(e -> handleAction("open"));
        MenuItem saveItem = createMenuItem("Save", "Ctrl+S");
        saveItem.setOnAction(e -> handleAction("save"));
        MenuItem saveAsItem = createMenuItem("Save As...", "Ctrl+Shift+S");
        MenuItem exportItem = createMenuItem("Export...", "Ctrl+E");
        MenuItem closeItem = createMenuItem("Close", "Ctrl+W");
        MenuItem exitItem = createMenuItem("Exit", "Ctrl+Q");
        exitItem.setOnAction(e -> stage.close());
        
        menu.getItems().addAll(
            newItem, new SeparatorMenuItem(),
            openItem, new SeparatorMenuItem(),
            saveItem, saveAsItem, exportItem, new SeparatorMenuItem(),
            closeItem, exitItem
        );
        
        btn.setOnAction(e -> menu.show(btn, javafx.geometry.Side.BOTTOM, 0, 0));
    }
    
    private void setupEditMenu(Button btn) {
        ContextMenu menu = new ContextMenu();
        menu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        
        MenuItem undoItem = createMenuItem("Undo", "Ctrl+Z");
        undoItem.setOnAction(e -> handleAction("undo"));
        MenuItem redoItem = createMenuItem("Redo", "Ctrl+Y");
        redoItem.setOnAction(e -> handleAction("redo"));
        MenuItem cutItem = createMenuItem("Cut", "Ctrl+X");
        MenuItem copyItem = createMenuItem("Copy", "Ctrl+C");
        MenuItem pasteItem = createMenuItem("Paste", "Ctrl+V");
        MenuItem selectAllItem = createMenuItem("Select All", "Ctrl+A");
        MenuItem preferencesItem = createMenuItem("Preferences...", "Ctrl+,");
        
        menu.getItems().addAll(
            undoItem, redoItem, new SeparatorMenuItem(),
            cutItem, copyItem, pasteItem, new SeparatorMenuItem(),
            selectAllItem, new SeparatorMenuItem(),
            preferencesItem
        );
        
        btn.setOnAction(e -> menu.show(btn, javafx.geometry.Side.BOTTOM, 0, 0));
    }
    
    private void setupImageMenu(Button btn) {
        ContextMenu menu = new ContextMenu();
        menu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        
        MenuItem adjustmentsItem = createMenuItem("Adjustments", "");
        MenuItem autoToneItem = createMenuItem("Auto Tone", "Ctrl+L");
        MenuItem autoContrastItem = createMenuItem("Auto Contrast", "Ctrl+Alt+L");
        MenuItem autoColorItem = createMenuItem("Auto Color", "Ctrl+Shift+L");
        MenuItem resizeItem = createMenuItem("Resize...", "Ctrl+Alt+I");
        MenuItem cropItem = createMenuItem("Crop", "C");
        MenuItem rotateItem = createMenuItem("Rotate...", "");
        
        menu.getItems().addAll(
            adjustmentsItem, new SeparatorMenuItem(),
            autoToneItem, autoContrastItem, autoColorItem, new SeparatorMenuItem(),
            resizeItem, cropItem, rotateItem
        );
        
        btn.setOnAction(e -> menu.show(btn, javafx.geometry.Side.BOTTOM, 0, 0));
    }
    
    private void setupFilterMenu(Button btn) {
        ContextMenu menu = new ContextMenu();
        menu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        
        MenuItem blurItem = createMenuItem("Blur", "");
        MenuItem sharpenItem = createMenuItem("Sharpen", "");
        MenuItem grayscaleItem = createMenuItem("Grayscale", "Ctrl+G");
        grayscaleItem.setOnAction(e -> handleAction("grayscale"));
        MenuItem sepiaItem = createMenuItem("Sepia Tone", "Ctrl+E");
        sepiaItem.setOnAction(e -> handleAction("sepia"));
        MenuItem invertItem = createMenuItem("Invert", "Ctrl+I");
        invertItem.setOnAction(e -> handleAction("invert"));
        MenuItem noiseItem = createMenuItem("Noise Reduction", "");
        
        menu.getItems().addAll(
            blurItem, sharpenItem, new SeparatorMenuItem(),
            grayscaleItem, sepiaItem, invertItem, new SeparatorMenuItem(),
            noiseItem
        );
        
        btn.setOnAction(e -> menu.show(btn, javafx.geometry.Side.BOTTOM, 0, 0));
    }
    
    private void setupViewMenu(Button btn) {
        ContextMenu menu = new ContextMenu();
        menu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        
        MenuItem zoomInItem = createMenuItem("Zoom In", "Ctrl++");
        zoomInItem.setOnAction(e -> handleAction("zoomIn"));
        MenuItem zoomOutItem = createMenuItem("Zoom Out", "Ctrl+-");
        zoomOutItem.setOnAction(e -> handleAction("zoomOut"));
        MenuItem fitScreenItem = createMenuItem("Fit to Screen", "Ctrl+0");
        fitScreenItem.setOnAction(e -> handleAction("fit"));
        MenuItem actualSizeItem = createMenuItem("100%", "Ctrl+1");
        
        CheckMenuItem showToolsItem = new CheckMenuItem("Show Tools");
        showToolsItem.setSelected(true);
        CheckMenuItem showLayersItem = new CheckMenuItem("Show Layers");
        showLayersItem.setSelected(true);
        CheckMenuItem showAdjustmentsItem = new CheckMenuItem("Show Adjustments");
        showAdjustmentsItem.setSelected(true);
        
        styleCheckMenuItem(showToolsItem);
        styleCheckMenuItem(showLayersItem);
        styleCheckMenuItem(showAdjustmentsItem);
        
        menu.getItems().addAll(
            zoomInItem, zoomOutItem, new SeparatorMenuItem(),
            fitScreenItem, actualSizeItem, new SeparatorMenuItem(),
            showToolsItem, showLayersItem, showAdjustmentsItem
        );
        
        btn.setOnAction(e -> menu.show(btn, javafx.geometry.Side.BOTTOM, 0, 0));
    }
    
    private void setupHelpMenu(Button btn) {
        ContextMenu menu = new ContextMenu();
        menu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 1;");
        
        MenuItem helpDocsItem = createMenuItem("Help Documentation", "F1");
        MenuItem shortcutsItem = createMenuItem("Keyboard Shortcuts", "");
        MenuItem aboutItem = createMenuItem("About Unmess", "");
        MenuItem checkUpdatesItem = createMenuItem("Check for Updates", "");
        
        menu.getItems().addAll(
            helpDocsItem, shortcutsItem, new SeparatorMenuItem(),
            aboutItem, checkUpdatesItem
        );
        
        btn.setOnAction(e -> menu.show(btn, javafx.geometry.Side.BOTTOM, 0, 0));
    }
    
    private MenuItem createMenuItem(String text, String shortcut) {
        MenuItem item = new MenuItem();
        
        HBox content = new HBox(40);
        content.setAlignment(Pos.CENTER_LEFT);
        
        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 13px;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label shortcutLabel = new Label(shortcut);
        shortcutLabel.setStyle("-fx-text-fill: #999999; -fx-font-size: 11px;");
        
        content.getChildren().addAll(textLabel, spacer, shortcutLabel);
        item.setGraphic(content);
        
        item.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-padding: 8 20 8 20;"
        );
        
        return item;
    }
    
    private void styleCheckMenuItem(CheckMenuItem item) {
        item.setStyle(
            "-fx-text-fill: #333333; " +
            "-fx-font-size: 13px; " +
            "-fx-padding: 8 20 8 20;"
        );
    }
    
    private void handleAction(String action) {
        if (actionHandler != null) {
            actionHandler.accept(action);
        }
    }
    
    public void setActionHandler(Consumer<String> handler) {
        this.actionHandler = handler;
    }
}
