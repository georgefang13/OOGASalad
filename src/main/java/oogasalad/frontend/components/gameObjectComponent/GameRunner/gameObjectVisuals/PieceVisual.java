package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
/**
 * @author Owen MacKenzie
 */
public abstract class PieceVisual extends AbstractSelectableVisual {
    protected Node pieceImage;

    /**
     * Constructor for PieceVisual
     * @param pieceImgPath
     * @param width
     * @param height
     * @param id
     */
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
