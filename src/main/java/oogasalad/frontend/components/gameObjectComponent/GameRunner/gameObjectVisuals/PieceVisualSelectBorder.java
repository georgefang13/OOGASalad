package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * @author Owen MacKenzie
 */
public class PieceVisualSelectBorder extends PieceVisual {
    private static final Color TRANSPARENT_FILL = Color.TRANSPARENT;
    private static final Color UNSELECTED_BORDER_COLOR = Color.TRANSPARENT;
    private final Rectangle highlightBorder;
    private Color selectedBorderColor;
    public PieceVisualSelectBorder(String pieceImgPath, String selectedBorderColor, int width, int height, String id) {
        super(pieceImgPath, width, height, id);
        highlightBorder = new Rectangle(width,height,TRANSPARENT_FILL);
        StackPane stackPane = new StackPane(highlightBorder,pieceImage);
        this.getChildren().add(stackPane);
        updateClickableVisual(selectedBorderColor);
    }
    @Override
    public void showClickable() {
        highlightBorder.setStroke(selectedBorderColor);
    }
    @Override
    public void showUnclickable() {
        highlightBorder.setStroke(UNSELECTED_BORDER_COLOR);
    }
    @Override
    public void updateClickableVisual(String selectedBorderColor) {
        this.selectedBorderColor = loadColorFromHex(selectedBorderColor);
    }
}
