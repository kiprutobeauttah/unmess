package com.example.unmess.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

// History panel showing operation history
public class HistoryPanel extends VBox {
    
    private ListView<String> historyList;
    
    public HistoryPanel() {
        super(10);
        setPadding(new Insets(15));
        setStyle("-fx-background-color: #ffffff;");
        
        // Title
        Label titleLabel = new Label("HISTORY");
        titleLabel.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 11px;");
        
        // History list
        historyList = new ListView<>();
        historyList.getItems().addAll(
            "Open Image",
            "Brightness +0.2",
            "Contrast +0.15",
            "Saturation -0.1",
            "Gaussian Blur (r=2)",
            "Sharpen +0.5"
        );
        historyList.getSelectionModel().select(historyList.getItems().size() - 1);
        historyList.setStyle(
            "-fx-background-color: #1e1e1e; " +
            "-fx-border-color: #3e3e42; " +
            "-fx-border-width: 1; " +
            "-fx-control-inner-background: #1e1e1e;"
        );
        
        // Add context menu to history list
        ContextMenu historyContextMenu = ContextMenuFactory.createHistoryContextMenu(action -> {
            if ("clearAll".equals(action)) {
                clearHistory();
            }
            System.out.println("History action: " + action);
        });
        historyList.setContextMenu(historyContextMenu);
        
        // Info label
        Label infoLabel = new Label("Click on any state to revert");
        infoLabel.setStyle(
            "-fx-text-fill: #888888; " +
            "-fx-font-size: 10px; " +
            "-fx-padding: 5 0 0 0;"
        );
        infoLabel.setWrapText(true);
        
        // Buttons
        HBox buttonBox = new HBox(8);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button clearBtn = new Button("Clear History");
        clearBtn.setPrefWidth(150);
        clearBtn.setStyle(
            "-fx-background-color: #3e3e42; " +
            "-fx-text-fill: #cccccc; " +
            "-fx-background-radius: 3; " +
            "-fx-font-size: 11px;"
        );
        clearBtn.setOnAction(e -> historyList.getItems().clear());
        
        buttonBox.getChildren().add(clearBtn);
        
        VBox.setVgrow(historyList, Priority.ALWAYS);
        getChildren().addAll(titleLabel, historyList, infoLabel, buttonBox);
    }
    
    public void addHistoryItem(String item) {
        historyList.getItems().add(item);
        historyList.getSelectionModel().select(historyList.getItems().size() - 1);
        historyList.scrollTo(historyList.getItems().size() - 1);
    }
    
    public void clearHistory() {
        historyList.getItems().clear();
    }
}
