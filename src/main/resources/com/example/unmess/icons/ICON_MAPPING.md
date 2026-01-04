# Icon Mapping Reference

All icons downloaded from Feather Icons (via Iconify API)
Color: #cccccc (light gray for dark theme)
Format: SVG

## File Operations
- `file-plus.svg` - New document
- `folder.svg` - Open folder
- `save.svg` - Save file

## Edit Operations
- `corner-up-left.svg` - Undo
- `corner-up-right.svg` - Redo
- `scissors.svg` - Cut
- `copy.svg` - Copy
- `clipboard.svg` - Paste

## View Operations
- `zoom-in.svg` - Zoom in
- `zoom-out.svg` - Zoom out
- `maximize.svg` - Fit to screen

## Tools
- `square.svg` - Selection tool
- `move.svg` - Move tool
- `crop.svg` - Crop tool
- `edit-3.svg` - Brush tool
- `delete.svg` - Eraser tool
- `type.svg` - Text tool
- `droplet.svg` - Eyedropper tool

## Panel Icons
- `sliders.svg` - Adjustments panel
- `layers.svg` - Layers panel
- `clock.svg` - History panel
- `list.svg` - Properties panel
- `filter.svg` - Filter menu

## Usage in JavaFX

```java
// Load SVG icon
ImageView icon = new ImageView(new Image(
    getClass().getResourceAsStream("/com/example/unmess/icons/save.svg")
));
icon.setFitWidth(24);
icon.setFitHeight(24);
```

## Total Icons: 23
All icons successfully downloaded and ready to use!
