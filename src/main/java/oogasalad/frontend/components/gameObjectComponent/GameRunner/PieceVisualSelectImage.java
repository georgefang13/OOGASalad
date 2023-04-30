package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;

public class PieceVisualSelectImage extends PieceVisual{
    private Node selectImage;
    public PieceVisualSelectImage(Node pieceImg, Node selectImage, double height, double width, String id) {
        super(pieceImg, height, width, id);
        this.selectImage = selectImage;
        this.getChildren().add(pieceImage);
    }
    private void switchImages(Node oldImage, Node newImage){
        this.getChildren().remove(oldImage);
        this.getChildren().add(newImage);
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
