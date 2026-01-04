package com.example.unmess.ui;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

// Professional adjustments panel with Photoshop-style controls
public class AdjustmentsPanel extends VBox {
    
    private Slider brightnessSlider;
    private Slider contrastSlider;
    private Slider saturationSlider;
    private Slider hueSlider;
    private Slider exposureSlider;
    private Slider highlightsSlider;
    private Slider shadowsSlider;
    private Slider whitesSlider;
    private Slider blacksSlider;
    private Slider claritySlider;
    private Slider vibranceSlider;
    private Slider blurSlider;
    private Slider sharpenSlider;
    private Slider noiseReductionSlider;
    
    private Consumer<Void> onAdjustmentChanged;
    
    public AdjustmentsPanel() {
        super(10);
        setPadding(new Insets(15));
        setPrefWidth(340);
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 0 1;");
        
        // Title
        Label titleLabel = new Label("ADJUSTMENTS");
        titleLabel.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 12px;");
        
        // Create tabbed sections
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-background-color: #ffffff;");
        
        // Basic adjustments tab
        Tab basicTab = new Tab("Basic");
        basicTab.setContent(createBasicAdjustments());
        
        // Tone curve tab
        Tab toneTab = new Tab("Tone");
        toneTab.setContent(createToneAdjustments());
        
        // Effects tab
        Tab effectsTab = new Tab("Effects");
        effectsTab.setContent(createEffectsAdjustments());
        
        tabPane.getTabs().addAll(basicTab, toneTab, effectsTab);
        
        // Action buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        
        Button resetBtn = new Button("Reset All");
        resetBtn.setPrefWidth(150);
        resetBtn.getStyleClass().add("reset-button");
        
        Button applyBtn = new Button("Apply");
        applyBtn.setPrefWidth(150);
        applyBtn.getStyleClass().add("apply-button");
        
        buttonBox.getChildren().addAll(resetBtn, applyBtn);
        
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        getChildren().addAll(titleLabel, new Separator(), tabPane, buttonBox);
    }
    
    private ScrollPane createBasicAdjustments() {
        VBox content = new VBox(12);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #fafafa;");
        
        brightnessSlider = createAdjustmentSlider("Brightness", -100, 100, 0);
        contrastSlider = createAdjustmentSlider("Contrast", -100, 100, 0);
        exposureSlider = createAdjustmentSlider("Exposure", -2.0, 2.0, 0);
        saturationSlider = createAdjustmentSlider("Saturation", -100, 100, 0);
        vibranceSlider = createAdjustmentSlider("Vibrance", -100, 100, 0);
        hueSlider = createAdjustmentSlider("Hue", -180, 180, 0);
        
        content.getChildren().addAll(
            createSliderGroup("Exposure", exposureSlider),
            createSliderGroup("Contrast", contrastSlider),
            createSliderGroup("Brightness", brightnessSlider),
            new Separator(),
            createSliderGroup("Saturation", saturationSlider),
            createSliderGroup("Vibrance", vibranceSlider),
            createSliderGroup("Hue", hueSlider)
        );
        
        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #fafafa;");
        return scroll;
    }
    
    private ScrollPane createToneAdjustments() {
        VBox content = new VBox(12);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #fafafa;");
        
        highlightsSlider = createAdjustmentSlider("Highlights", -100, 100, 0);
        shadowsSlider = createAdjustmentSlider("Shadows", -100, 100, 0);
        whitesSlider = createAdjustmentSlider("Whites", -100, 100, 0);
        blacksSlider = createAdjustmentSlider("Blacks", -100, 100, 0);
        claritySlider = createAdjustmentSlider("Clarity", -100, 100, 0);
        
        content.getChildren().addAll(
            createSliderGroup("Highlights", highlightsSlider),
            createSliderGroup("Shadows", shadowsSlider),
            new Separator(),
            createSliderGroup("Whites", whitesSlider),
            createSliderGroup("Blacks", blacksSlider),
            new Separator(),
            createSliderGroup("Clarity", claritySlider)
        );
        
        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #fafafa;");
        return scroll;
    }
    
    private ScrollPane createEffectsAdjustments() {
        VBox content = new VBox(12);
        content.setPadding(new Insets(15));
        content.setStyle("-fx-background-color: #fafafa;");
        
        sharpenSlider = createAdjustmentSlider("Sharpen", 0, 100, 0);
        blurSlider = createAdjustmentSlider("Blur", 0, 10, 0);
        noiseReductionSlider = createAdjustmentSlider("Noise Reduction", 0, 100, 0);
        
        content.getChildren().addAll(
            createSliderGroup("Sharpen", sharpenSlider),
            createSliderGroup("Blur", blurSlider),
            createSliderGroup("Noise Reduction", noiseReductionSlider)
        );
        
        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #fafafa;");
        return scroll;
    }
    
    private Slider createAdjustmentSlider(String name, double min, double max, double initial) {
        Slider slider = new Slider(min, max, initial);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(false);
        slider.setBlockIncrement((max - min) / 100);
        slider.valueProperty().addListener((obs, old, val) -> {
            if (onAdjustmentChanged != null) {
                onAdjustmentChanged.accept(null);
            }
        });
        return slider;
    }
    
    private VBox createSliderGroup(String label, Slider slider) {
        VBox group = new VBox(6);
        group.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        );
        
        HBox labelBox = new HBox(10);
        labelBox.setAlignment(Pos.CENTER_LEFT);
        
        Label nameLabel = new Label(label);
        nameLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12px; -fx-font-weight: 500;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label valueLabel = new Label(formatValue(slider.getValue(), slider.getMin(), slider.getMax()));
        valueLabel.setStyle(
            "-fx-text-fill: #2196F3; " +
            "-fx-font-size: 11px; " +
            "-fx-font-weight: bold; " +
            "-fx-font-family: 'Consolas', monospace;"
        );
        
        slider.valueProperty().addListener((obs, old, val) -> 
            valueLabel.setText(formatValue(val.doubleValue(), slider.getMin(), slider.getMax()))
        );
        
        // Reset button for individual slider
        Button resetBtn = new Button("↻");
        resetBtn.setPrefSize(24, 24);
        resetBtn.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-text-fill: #666666; " +
            "-fx-font-size: 12px; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        );
        resetBtn.setOnAction(e -> slider.setValue(0));
        resetBtn.setOnMouseEntered(e -> resetBtn.setStyle(
            "-fx-background-color: #2196F3; " +
            "-fx-text-fill: #ffffff; " +
            "-fx-font-size: 12px; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #2196F3; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        ));
        resetBtn.setOnMouseExited(e -> resetBtn.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-text-fill: #666666; " +
            "-fx-font-size: 12px; " +
            "-fx-background-radius: 4; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4;"
        ));
        
        labelBox.getChildren().addAll(nameLabel, spacer, valueLabel, resetBtn);
        
        group.getChildren().addAll(labelBox, slider);
        return group;
    }
    
    private String formatValue(double value, double min, double max) {
        if (max <= 10) {
            return String.format("%.2f", value);
        } else if (max <= 100) {
            return String.format("%d", (int) value);
        } else {
            return String.format("%.0f°", value);
        }
    }
    
    // Getters for slider values
    public double getBrightness() { return brightnessSlider.getValue() / 100.0; }
    public double getContrast() { return contrastSlider.getValue() / 100.0; }
    public double getSaturation() { return saturationSlider.getValue() / 100.0; }
    public double getBlur() { return blurSlider.getValue(); }
    public double getSharpen() { return sharpenSlider.getValue() / 50.0; }
    
    public void setOnAdjustmentChanged(Consumer<Void> callback) {
        this.onAdjustmentChanged = callback;
    }
    
    public void resetAll() {
        brightnessSlider.setValue(0);
        contrastSlider.setValue(0);
        saturationSlider.setValue(0);
        hueSlider.setValue(0);
        exposureSlider.setValue(0);
        highlightsSlider.setValue(0);
        shadowsSlider.setValue(0);
        whitesSlider.setValue(0);
        blacksSlider.setValue(0);
        claritySlider.setValue(0);
        vibranceSlider.setValue(0);
        blurSlider.setValue(0);
        sharpenSlider.setValue(0);
        noiseReductionSlider.setValue(0);
    }
}
