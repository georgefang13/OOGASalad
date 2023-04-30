package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;

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
    public Node getImage(){
        return pieceImage;
    }
}
