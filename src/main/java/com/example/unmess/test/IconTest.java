package com.example.unmess.test;

import com.example.unmess.ui.IconLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Test application to verify all icons load correctly
public class IconTest extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FlowPane flowPane = new FlowPane(20, 20);
        flowPane.setPadding(new Insets(20));
        flowPane.setStyle("-fx-background-color: #ffffff;");
        
        // Test all icons
        addIcon(flowPane, "New", IconLoader.getNewIcon(32));
        addIcon(flowPane, "Open", IconLoader.getOpenIcon(32));
        addIcon(flowPane, "Save", IconLoader.getSaveIcon(32));
        addIcon(flowPane, "Undo", IconLoader.getUndoIcon(32));
        addIcon(flowPane, "Redo", IconLoader.getRedoIcon(32));
        addIcon(flowPane, "Cut", IconLoader.getCutIcon(32));
        addIcon(flowPane, "Copy", IconLoader.getCopyIcon(32));
        addIcon(flowPane, "Paste", IconLoader.getPasteIcon(32));
        addIcon(flowPane, "Zoom In", IconLoader.getZoomInIcon(32));
        addIcon(flowPane, "Zoom Out", IconLoader.getZoomOutIcon(32));
        addIcon(flowPane, "Fit", IconLoader.getFitIcon(32));
        addIcon(flowPane, "Selection", IconLoader.getSelectionIcon(32));
        addIcon(flowPane, "Move", IconLoader.getMoveIcon(32));
        addIcon(flowPane, "Crop", IconLoader.getCropIcon(32));
        addIcon(flowPane, "Brush", IconLoader.getBrushIcon(32));
        addIcon(flowPane, "Eraser", IconLoader.getEraserIcon(32));
        addIcon(flowPane, "Text", IconLoader.getTextIcon(32));
        addIcon(flowPane, "Eyedropper", IconLoader.getEyedropperIcon(32));
        addIcon(flowPane, "Adjustments", IconLoader.getAdjustmentsIcon(32));
        addIcon(flowPane, "Layers", IconLoader.getLayersIcon(32));
        addIcon(flowPane, "History", IconLoader.getHistoryIcon(32));
        addIcon(flowPane, "Properties", IconLoader.getPropertiesIcon(32));
        addIcon(flowPane, "Filter", IconLoader.getFilterIcon(32));
        
        Scene scene = new Scene(flowPane, 800, 600);
        primaryStage.setTitle("Icon Test - All Icons Should Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void addIcon(FlowPane pane, String name, Node icon) {
        VBox box = new VBox(5);
        box.setStyle(
            "-fx-alignment: center; " +
            "-fx-padding: 10; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-background-color: #fafafa;"
        );
        
        Label label = new Label(name);
        label.setStyle("-fx-text-fill: #333333; -fx-font-size: 11px;");
        
        if (icon == null) {
            Label errorLabel = new Label("❌");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 24px;");
            box.getChildren().addAll(errorLabel, label);
        } else {
            box.getChildren().addAll(icon, label);
        }
        
        pane.getChildren().add(box);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
