package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PieceVisualSelectBorder extends PieceVisual{
    private static final Color UNSELECTED_FILL_COLOR = Color.TRANSPARENT;
    private static final Color UNSELECTED_BORDER_COLOR = Color.TRANSPARENT;
    private Rectangle highlightBorder;
    private Color selectedBorderColor;
    public PieceVisualSelectBorder(Node pieceImg, Color selectedBorderColor, double height, double width, String id) {
        super(pieceImg, height, width, id);
        this.selectedBorderColor = selectedBorderColor;
        highlightBorder = new Rectangle(height,width,UNSELECTED_FILL_COLOR);
        highlightBorder.setStroke(UNSELECTED_BORDER_COLOR);
        StackPane stackPane = new StackPane(highlightBorder,pieceImage);
        this.getChildren().add(stackPane);
    }
    @Override
    public void showClickable() {
        highlightBorder.setStroke(selectedBorderColor);
    }
    @Override
    public void showUnclickable() {
        highlightBorder.setStroke(UNSELECTED_BORDER_COLOR);
    }
}
