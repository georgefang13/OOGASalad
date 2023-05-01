package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;

public abstract class PieceVisual extends AbstractSelectableVisual {
    protected Node pieceImage;
    public PieceVisual(String pieceImgPath, int width, int height, String id) {
        super(id,width,height);
        initHBox();
        updatePieceImage(pieceImgPath);
    }
    private void initHBox() {
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
    }
    protected void updatePieceImage(String imgPath){
        pieceImage = loadVisual(imgPath);
    }
}
