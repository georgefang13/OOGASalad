package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PieceVisualSelectBorder extends PieceVisual {
    private static final Color UNSELECTED_FILL_COLOR = Color.TRANSPARENT;
    private static final Color UNSELECTED_BORDER_COLOR = Color.TRANSPARENT;
    private final Rectangle highlightBorder;
    private final Color selectedBorderColor;
    public PieceVisualSelectBorder(Node pieceImg, Color selectedBorderColor, double height, double width, String id) {
        super(pieceImg, height, width, id);
        this.selectedBorderColor = selectedBorderColor;
        highlightBorder = new Rectangle(width,height,UNSELECTED_FILL_COLOR);
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

    @Override
    public void updateClickableVisual(Node newVisual) {
        Rectangle r = (Rectangle) newVisual;
        highlightBorder.setStroke(r.getFill());
    }

    @Override
    public void updateUnClickableVisual(Node newVisual) {

    }
}
