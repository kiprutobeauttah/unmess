# Unmess

A desktop photo editor built with JavaFX featuring a modern interface and comprehensive image editing tools.

## Features

**Image Adjustments**
- Basic adjustments: Exposure, Contrast, Brightness, Saturation, Vibrance, Hue
- Tone controls: Highlights, Shadows, Whites, Blacks, Clarity
- Effects: Sharpen, Blur, Noise Reduction
- Filters: Grayscale, Sepia, Invert

**Editing Tools**
- Selection, Move, Crop
- Brush, Eraser, Text
- Eyedropper, Zoom

**Layer Management**
- Multiple layers with blend modes
- Opacity control
- Layer visibility toggle

**History & Undo**
- 20-level undo/redo system
- Memory usage tracking

## Getting Started

**Requirements**
- Java 21 or higher
- Maven

**Run the application**
```bash
mvn clean javafx:run
```

## Keyboard Shortcuts

**File**: `Ctrl+N` New | `Ctrl+O` Open | `Ctrl+S` Save | `Ctrl+Shift+S` Save As  
**Edit**: `Ctrl+Z` Undo | `Ctrl+Y` Redo | `Ctrl+X` Cut | `Ctrl+C` Copy | `Ctrl+V` Paste  
**View**: `Ctrl++` Zoom In | `Ctrl+-` Zoom Out | `Ctrl+0` Fit | `Ctrl+1` 100%  
**Image**: `Ctrl+L` Auto Tone | `Ctrl+Alt+L` Auto Contrast | `Ctrl+Shift+L` Auto Color

## Architecture

```
core/          - Core utilities (Constants, Logger, Validator)
model/         - Data models (ImageState, HistoryManager, Operation)
engine/        - Image processing engine
ui/            - User interface components
```

## Technology

- Java 21
- JavaFX 21.0.6
- Maven
