# Unmess - Professional Photo Editor

Photoshop-grade photo editing application with NASA-level engineering and professional UI design.

## Features

**Professional UI**
- Photoshop-style interface with vertical toolbar, adjustments panel, layers panel
- Dark professional theme optimized for long editing sessions
- Real-time preview with live adjustments
- Zoom controls (fit, actual size, zoom in/out)
- Comprehensive keyboard shortcuts

**Adjustments** (Tabbed Interface)
- Basic: Exposure, Contrast, Brightness, Saturation, Vibrance, Hue
- Tone: Highlights, Shadows, Whites, Blacks, Clarity
- Effects: Sharpen, Blur, Noise Reduction

**Filters**: Grayscale, Sepia, Invert  
**Tools**: Selection, Move, Crop, Brush, Eraser, Text, Eyedropper, Zoom  
**Layers**: Layer management with blend modes and opacity control  
**History**: 20-level undo/redo with memory tracking

## Run

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
core/          - Foundation (Constants, Logger, Validator)
model/         - Domain model (ImageState, HistoryManager, Operation)
engine/        - Image processing (ImageProcessorV2, PixelOperations)
ui/            - Professional UI components (ToolsPanel, AdjustmentsPanel, LayersPanel)
UnmessApp      - Main application with Photoshop-grade interface
```

## Tech Stack

Java 21 | JavaFX 21.0.6 | Maven

## UI Components

- **ToolsPanel**: Vertical toolbar with 8+ professional tools
- **AdjustmentsPanel**: Tabbed adjustments (Basic/Tone/Effects) with individual reset buttons
- **LayersPanel**: Layer management with blend modes and opacity
- **PropertiesPanel**: Image info and history tracking
- **Status Bar**: Real-time feedback with zoom level and tool info
