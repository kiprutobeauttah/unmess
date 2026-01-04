package com.example.unmess;

import com.example.unmess.core.Logger;
import com.example.unmess.core.ValidationException;
import com.example.unmess.engine.ImageProcessorV2;
import com.example.unmess.model.HistoryManager;
import com.example.unmess.model.ImageState;
import com.example.unmess.model.Operation;
import com.example.unmess.ui.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

// Unmess - Professional Photo Editor with Photoshop-grade controls
public class UnmessApp extends Application {
    
    private ImageView imageView;
    private ImageState imageState;
    private HistoryManager historyManager;
    private Label statusLabel;
    private ScrollPane scrollPane;
    
    // UI Panels
    private ToolsPanel toolsPanel;
    private TopToolBar topToolBar;
    private RightPanel rightPanel;
    
    private double currentZoom = 1.0;
    
    @Override
    public void start(Stage primaryStage) {
        Logger.info("Starting Unmess Professional Photo Editor");
        primaryStage.setTitle("Unmess");
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);
        
        historyManager = new HistoryManager();
        
        BorderPane root = new BorderPane();
        
        // Top: Modern menu bar + Top toolbar
        VBox topContainer = new VBox(0);
        ModernMenuBar modernMenuBar = new ModernMenuBar(primaryStage);
        modernMenuBar.setActionHandler(this::handleMenuAction);
        topToolBar = new TopToolBar();
        topToolBar.setActionHandler(this::handleToolbarAction);
        topContainer.getChildren().addAll(modernMenuBar, topToolBar);
        root.setTop(topContainer);
        
        // Left: Vertical tools panel
        toolsPanel = new ToolsPanel();
        root.setLeft(toolsPanel);
        
        // Center: Image canvas
        root.setCenter(createImageCanvas());
        
        // Right: Tabbed panel with adjustments, layers, history, properties
        rightPanel = new RightPanel();
        rightPanel.getAdjustmentsPanel().setOnAdjustmentChanged(v -> applyAdjustments());
        root.setRight(rightPanel);
        
        // Bottom: Status bar
        root.setBottom(createStatusBar());
        
        Scene scene = new Scene(root, 1600, 1000);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Logger.info("Unmess UI initialized successfully");
    }
    
    // Handle menu actions
    private void handleMenuAction(String action) {
        Stage stage = (Stage) topToolBar.getScene().getWindow();
        handleToolbarAction(action);
    }
    
    // Handle toolbar actions
    private void handleToolbarAction(String action) {
        Stage stage = (Stage) topToolBar.getScene().getWindow();
        
        switch (action) {
            case "new":
                // New image dialog
                break;
            case "open":
                openImage(stage);
                break;
            case "save":
                saveImage(stage);
                break;
            case "undo":
                undo();
                break;
            case "redo":
                redo();
                break;
            case "cut":
            case "copy":
            case "paste":
                // Implement clipboard operations
                updateStatus("Clipboard operation: " + action);
                break;
            case "zoomIn":
                zoomIn();
                break;
            case "zoomOut":
                zoomOut();
                break;
            case "fit":
                fitToScreen();
                break;
            case "crop":
            case "brush":
            case "text":
                // Tool selection
                updateStatus("Tool selected: " + action);
                break;
        }
    }
    
    // Handle context menu actions
    private void handleContextMenuAction(String action) {
        Logger.info("Context menu action: " + action);
        
        switch (action) {
            case "cut":
            case "copy":
            case "paste":
            case "duplicate":
                updateStatus("Clipboard: " + action);
                break;
            case "rotateCW":
                applyTransform("rotateCW");
                break;
            case "rotateCCW":
                applyTransform("rotateCCW");
                break;
            case "flipH":
                applyTransform("flipH");
                break;
            case "flipV":
                applyTransform("flipV");
                break;
            case "grayscale":
            case "sepia":
            case "invert":
                applyFilter(action);
                break;
            case "zoomIn":
                zoomIn();
                break;
            case "zoomOut":
                zoomOut();
                break;
            case "fit":
                fitToScreen();
                break;
            case "actualSize":
                actualSize();
                break;
            case "selectAll":
            case "deselect":
                updateStatus("Selection: " + action);
                break;
            default:
                if (action.startsWith("blendMode:")) {
                    String mode = action.substring(10);
                    updateStatus("Blend mode: " + mode);
                } else {
                    updateStatus("Action: " + action);
                }
                break;
        }
    }
    
    // Create image canvas with zoom controls
    private BorderPane createImageCanvas() {
        BorderPane canvas = new BorderPane();
        canvas.setStyle("-fx-background-color: #fafafa;");
        
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.getStyleClass().add("image-container");
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setMinSize(600, 600);
        
        // Add context menu to image container
        ContextMenu imageContextMenu = ContextMenuFactory.createImageContextMenu(this::handleContextMenuAction);
        imageContainer.setOnContextMenuRequested(e -> 
            imageContextMenu.show(imageContainer, e.getScreenX(), e.getScreenY())
        );
        
        scrollPane = new ScrollPane(imageContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        canvas.setCenter(scrollPane);
        return canvas;
    }
    
    // Create status bar
    private HBox createStatusBar() {
        HBox statusBar = new HBox(20);
        statusBar.setPadding(new Insets(6, 10, 6, 10));
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.getStyleClass().add("status-bar");
        
        statusLabel = new Label("Ready");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label zoomLabel = new Label("100%");
        zoomLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-family: monospace; -fx-font-weight: bold;");
        
        Label toolLabel = new Label("Tool: Move");
        toolLabel.setStyle("-fx-text-fill: #ffffff;");
        
        statusBar.getChildren().addAll(statusLabel, spacer, toolLabel, new Separator(javafx.geometry.Orientation.VERTICAL), zoomLabel);
        return statusBar;
    }
    
    // Image operations
    private void openImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image - Unmess");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.tiff"),
            new FileChooser.ExtensionFilter("PNG Images", "*.png"),
            new FileChooser.ExtensionFilter("JPEG Images", "*.jpg", "*.jpeg"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                imageState = ImageState.fromImage(image, file.getAbsolutePath());
                historyManager.clear();
                imageView.setImage(imageState.getCurrentImage());
                rightPanel.getAdjustmentsPanel().resetAll();
                fitToScreen();
                updateStatus("Loaded: " + file.getName() + " (" + 
                    (int)image.getWidth() + "x" + (int)image.getHeight() + " px)");
                Logger.info("Image loaded: " + file.getAbsolutePath());
            } catch (ValidationException e) {
                showError("Validation failed: " + e.getMessage());
                Logger.error("Image validation failed", e);
            } catch (Exception e) {
                showError("Failed to load image: " + e.getMessage());
                Logger.error("Failed to load image", e);
            }
        }
    }
    
    private void saveImage(Stage stage) {
        if (imageState == null) {
            showError("No image to save");
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image - Unmess");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG Image", "*.png"),
            new FileChooser.ExtensionFilter("JPEG Image", "*.jpg", "*.jpeg")
        );
        
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                javax.imageio.ImageIO.write(
                    javafx.embed.swing.SwingFXUtils.fromFXImage(imageState.getCurrentImage(), null),
                    getFileExtension(file),
                    file
                );
                updateStatus("Saved: " + file.getName());
                Logger.info("Image saved: " + file.getAbsolutePath());
            } catch (Exception e) {
                showError("Failed to save image: " + e.getMessage());
                Logger.error("Failed to save image", e);
            }
        }
    }
    
    private void applyAdjustments() {
        if (imageState == null) return;
        
        try {
            WritableImage result = imageState.getOriginalImage();
            
            // Apply adjustments from the panel
            AdjustmentsPanel adjustPanel = rightPanel.getAdjustmentsPanel();
            double brightness = adjustPanel.getBrightness();
            double contrast = adjustPanel.getContrast();
            double saturation = adjustPanel.getSaturation();
            double blur = adjustPanel.getBlur();
            double sharpen = adjustPanel.getSharpen();
            
            if (brightness != 0) {
                result = ImageProcessorV2.adjustBrightness(result, brightness);
            }
            if (contrast != 0) {
                result = ImageProcessorV2.adjustContrast(result, contrast);
            }
            if (saturation != 0) {
                result = ImageProcessorV2.adjustSaturation(result, saturation);
            }
            if (blur > 0) {
                result = ImageProcessorV2.gaussianBlur(result, (int)blur);
            }
            if (sharpen > 0) {
                result = ImageProcessorV2.sharpen(result, sharpen);
            }
            
            imageView.setImage(result);
            updateStatus("Adjustments applied (preview)");
        } catch (ValidationException e) {
            showError("Adjustment failed: " + e.getMessage());
            Logger.error("Adjustment validation failed", e);
        }
    }
    
    private void applyFilter(String filterType) {
        if (imageState == null) {
            showError("No image loaded");
            return;
        }
        
        try {
            historyManager.saveState(imageState);
            WritableImage result = null;
            Operation.Type opType = null;
            
            switch (filterType) {
                case "grayscale":
                    result = ImageProcessorV2.toGrayscale(imageState.getCurrentImage());
                    opType = Operation.Type.GRAYSCALE;
                    break;
                case "sepia":
                    result = ImageProcessorV2.sepiaTone(imageState.getCurrentImage());
                    opType = Operation.Type.SEPIA;
                    break;
                case "invert":
                    result = ImageProcessorV2.invert(imageState.getCurrentImage());
                    opType = Operation.Type.INVERT;
                    break;
            }
            
            if (result != null) {
                Operation operation = new Operation.Builder()
                        .type(opType)
                        .build();
                
                imageState = imageState.withImage(result, operation);
                imageView.setImage(result);
                rightPanel.getHistoryPanel().addHistoryItem("Filter: " + filterType);
                updateStatus("Filter applied: " + filterType);
                Logger.info("Filter applied: " + filterType);
            }
        } catch (ValidationException e) {
            showError("Filter failed: " + e.getMessage());
            Logger.error("Filter validation failed", e);
        }
    }
    
    // Apply transform operations (rotate, flip)
    private void applyTransform(String transformType) {
        if (imageState == null) {
            showError("No image loaded");
            return;
        }
        
        try {
            historyManager.saveState(imageState);
            WritableImage result = null;
            Operation.Type opType = null;
            
            switch (transformType) {
                case "rotateCW":
                    result = ImageProcessorV2.rotate90(imageState.getCurrentImage(), true);
                    opType = Operation.Type.ROTATE_CW;
                    break;
                case "rotateCCW":
                    result = ImageProcessorV2.rotate90(imageState.getCurrentImage(), false);
                    opType = Operation.Type.ROTATE_CCW;
                    break;
                case "flipH":
                    result = ImageProcessorV2.flip(imageState.getCurrentImage(), true);
                    opType = Operation.Type.FLIP_H;
                    break;
                case "flipV":
                    result = ImageProcessorV2.flip(imageState.getCurrentImage(), false);
                    opType = Operation.Type.FLIP_V;
                    break;
            }
            
            if (result != null) {
                Operation operation = new Operation.Builder()
                        .type(opType)
                        .build();
                
                imageState = imageState.withImage(result, operation);
                imageView.setImage(result);
                rightPanel.getHistoryPanel().addHistoryItem("Transform: " + transformType);
                updateStatus("Transform applied: " + transformType);
                Logger.info("Transform applied: " + transformType);
            }
        } catch (ValidationException e) {
            showError("Transform failed: " + e.getMessage());
            Logger.error("Transform validation failed", e);
        }
    }
    
    private void undo() {
        if (historyManager.canUndo()) {
            ImageState previous = historyManager.undo();
            if (previous != null) {
                imageState = previous;
                imageView.setImage(previous.getCurrentImage());
                updateStatus("Undo applied");
                Logger.info("Undo: " + historyManager.getStatistics());
            }
        } else {
            updateStatus("Nothing to undo");
        }
    }
    
    private void redo() {
        if (historyManager.canRedo()) {
            ImageState next = historyManager.redo();
            if (next != null) {
                imageState = next;
                imageView.setImage(next.getCurrentImage());
                updateStatus("Redo applied");
                Logger.info("Redo: " + historyManager.getStatistics());
            }
        } else {
            updateStatus("Nothing to redo");
        }
    }
    
    // Zoom controls
    private void zoomIn() {
        currentZoom = Math.min(currentZoom * 1.2, 8.0);
        applyZoom();
    }
    
    private void zoomOut() {
        currentZoom = Math.max(currentZoom / 1.2, 0.1);
        applyZoom();
    }
    
    private void fitToScreen() {
        if (imageView.getImage() != null) {
            double imageWidth = imageView.getImage().getWidth();
            double imageHeight = imageView.getImage().getHeight();
            double viewWidth = scrollPane.getViewportBounds().getWidth();
            double viewHeight = scrollPane.getViewportBounds().getHeight();
            
            currentZoom = Math.min(viewWidth / imageWidth, viewHeight / imageHeight) * 0.95;
            applyZoom();
        }
    }
    
    private void actualSize() {
        currentZoom = 1.0;
        applyZoom();
    }
    
    private void applyZoom() {
        if (imageView.getImage() != null) {
            imageView.setFitWidth(imageView.getImage().getWidth() * currentZoom);
            imageView.setFitHeight(imageView.getImage().getHeight() * currentZoom);
            topToolBar.updateZoom(currentZoom);
        }
    }
    
    // Helper methods
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Operation Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return lastDot > 0 ? name.substring(lastDot + 1) : "png";
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
