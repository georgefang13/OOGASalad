package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PieceVisual extends AbstractSelectableVisual{
    private Rectangle highlightBorder;
    private Node pieceImage;

    public PieceVisual(Node pieceImg, double size, String id) {
        super(id);
        initHBox(size);
        pieceImage = pieceImg;
        highlightBorder = new Rectangle(size,size,Color.TRANSPARENT);
        highlightBorder.setStroke(Color.TRANSPARENT);
        StackPane stackPane = new StackPane(highlightBorder,pieceImage);
        this.getChildren().add(stackPane);
    }
    private void initHBox(double size){
        this.setPrefHeight(size);
        this.setPrefWidth(size);
        this.setMaxHeight(size);
        this.setMaxWidth(size);
    }

    @Override
    public void showClickable() {
        highlightBorder.setStroke(Color.YELLOW);
    }

    @Override
    public void showUnclickable() {
        highlightBorder.setStroke(Color.TRANSPARENT);
    }

    @Override
    public void updateVisual(Node newVisual) {
        pieceImage = newVisual;
    }
}
