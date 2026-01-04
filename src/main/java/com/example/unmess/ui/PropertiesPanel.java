package com.example.unmess.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// Photoshop-style properties/adjustments panel
public class PropertiesPanel extends VBox {
    
    private Accordion accordion;
    
    public PropertiesPanel() {
        super(5);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #ffffff;");
        
        // Title
        Label titleLabel = new Label("PROPERTIES");
        titleLabel.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 11px;");
        
        accordion = new Accordion();
        accordion.setStyle("-fx-background-color: #ffffff;");
        
        // Image info section
        TitledPane infoPane = createInfoSection();
        
        // History section
        TitledPane historyPane = createHistorySection();
        
        accordion.getPanes().addAll(infoPane, historyPane);
        accordion.setExpandedPane(infoPane);
        
        VBox.setVgrow(accordion, Priority.ALWAYS);
        getChildren().addAll(titleLabel, accordion);
    }
    
    private TitledPane createInfoSection() {
        VBox content = new VBox(8);
        content.setPadding(new Insets(10));
        content.setStyle("-fx-background-color: #fafafa;");
        
        content.getChildren().addAll(
            createInfoRow("Dimensions:", "0 x 0 px"),
            createInfoRow("File Size:", "0 KB"),
            createInfoRow("Color Mode:", "RGB"),
            createInfoRow("Bit Depth:", "8 bit"),
            createInfoRow("Resolution:", "72 ppi")
        );
        
        TitledPane pane = new TitledPane("Image Info", content);
        pane.setStyle("-fx-text-fill: #333333;");
        return pane;
    }
    
    private TitledPane createHistorySection() {
        VBox content = new VBox(5);
        content.setPadding(new Insets(10));
        content.setStyle("-fx-background-color: #fafafa;");
        
        ListView<String> historyList = new ListView<>();
        historyList.getItems().addAll("Open", "Brightness +0.2", "Contrast +0.1");
        historyList.setPrefHeight(150);
        historyList.setStyle(
            "-fx-background-color: #1e1e1e; " +
            "-fx-border-color: #3e3e42; " +
            "-fx-border-width: 1;"
        );
        
        content.getChildren().add(historyList);
        
        TitledPane pane = new TitledPane("History", content);
        pane.setStyle("-fx-text-fill: #333333;");
        return pane;
    }
    
    private HBox createInfoRow(String label, String value) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        
        Label labelNode = new Label(label);
        labelNode.setStyle("-fx-text-fill: #888888; -fx-font-size: 11px;");
        labelNode.setPrefWidth(90);
        
        Label valueNode = new Label(value);
        valueNode.setStyle("-fx-text-fill: #333333; -fx-font-size: 11px; -fx-font-family: monospace;");
        
        row.getChildren().addAll(labelNode, valueNode);
        return row;
    }
    
    public void updateImageInfo(int width, int height, long fileSize) {
        // Update info display (implementation for dynamic updates)
    }
}
