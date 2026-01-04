package com.example.unmess.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;

// Professional right panel with tabbed interface
public class RightPanel extends VBox {
    
    private TabPane tabPane;
    private AdjustmentsPanel adjustmentsPanel;
    private LayersPanel layersPanel;
    private PropertiesPanel propertiesPanel;
    private HistoryPanel historyPanel;
    
    public RightPanel() {
        super(0);
        setPrefWidth(340);
        setMinWidth(300);
        setMaxWidth(400);
        setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 0 0 0 1;"
        );
        
        // Create tab pane
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);
        tabPane.setStyle("-fx-background-color: #ffffff;");
        
        // Create tabs with icons
        Tab adjustTab = createTab("Adjustments", IconLoader.getAdjustmentsIcon(16));
        adjustmentsPanel = new AdjustmentsPanel();
        adjustTab.setContent(adjustmentsPanel);
        
        Tab layersTab = createTab("Layers", IconLoader.getLayersIcon(16));
        layersPanel = new LayersPanel();
        layersTab.setContent(layersPanel);
        
        Tab historyTab = createTab("History", IconLoader.getHistoryIcon(16));
        historyPanel = new HistoryPanel();
        historyTab.setContent(historyPanel);
        
        Tab propsTab = createTab("Properties", IconLoader.getPropertiesIcon(16));
        propertiesPanel = new PropertiesPanel();
        propsTab.setContent(propertiesPanel);
        
        tabPane.getTabs().addAll(adjustTab, layersTab, historyTab, propsTab);
        tabPane.getSelectionModel().select(adjustTab);
        
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        getChildren().add(tabPane);
    }
    
    private Tab createTab(String text, javafx.scene.Node icon) {
        Tab tab = new Tab();
        
        // Create tab header with icon and text
        HBox header = new HBox(6);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(4, 8, 4, 8));
        
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #333333; -fx-font-size: 11px; -fx-font-weight: 500;");
        
        header.getChildren().addAll(icon, label);
        tab.setGraphic(header);
        
        return tab;
    }
    
    public AdjustmentsPanel getAdjustmentsPanel() {
        return adjustmentsPanel;
    }
    
    public LayersPanel getLayersPanel() {
        return layersPanel;
    }
    
    public PropertiesPanel getPropertiesPanel() {
        return propertiesPanel;
    }
    
    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }
}
