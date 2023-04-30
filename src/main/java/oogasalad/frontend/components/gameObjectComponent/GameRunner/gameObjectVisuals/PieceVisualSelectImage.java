package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;

public class PieceVisualSelectImage extends PieceVisual {
    private final Node selectImage;
    public PieceVisualSelectImage(Node pieceImg, Node selectImage, double height, double width, String id) {
        super(pieceImg, height, width, id);
        this.selectImage = selectImage;
        this.getChildren().add(pieceImage);
    }
    @Override
    public void showClickable() {
        switchImages(pieceImage,selectImage);
    }
    @Override
    public void showUnclickable() {
        switchImages(selectImage,pieceImage);
    }
}
