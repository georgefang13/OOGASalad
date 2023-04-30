package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.AbstractSelectableVisual;

public abstract class PieceVisual extends AbstractSelectableVisual {
    protected Node pieceImage;
    public PieceVisual(Node pieceImg, double height, double width, String id) {
        super(id);
        initHBox(height,width);
        pieceImage = pieceImg;
    }
    private void initHBox(double height, double width) {
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
    }
    @Override
    public void updateVisual(Node newVisual) {
        pieceImage = newVisual;
    }
}
