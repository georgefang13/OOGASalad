package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
/**
 * @author Owen MacKenzie
 */
public class PieceVisualSelectImage extends PieceVisual {
    private Node selectImage;
    public PieceVisualSelectImage(String pieceImg, String selectImage, int width, int height, String id) {
        super(pieceImg, width, height, id);
        this.getChildren().add(pieceImage);
        updateClickableVisual(selectImage);
    }
    @Override
    public void showClickable() {
        switchImages(pieceImage,selectImage);
    }
    @Override
    public void showUnclickable() {
        switchImages(selectImage,pieceImage);
    }
    @Override
    public void updateClickableVisual(String imgPath) {
        selectImage = loadVisual(imgPath);
    }

}
