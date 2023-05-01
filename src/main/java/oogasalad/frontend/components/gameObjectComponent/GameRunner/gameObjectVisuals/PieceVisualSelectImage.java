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

    /**
     * Switches the images of the clickable piece to the select image
     */
    @Override
    public void showClickable() {
        switchImages(pieceImage,selectImage);
    }

    /**
     * Switches the images of the unclickable piece to the piece image
     */
    @Override
    public void showUnclickable() {
        switchImages(selectImage,pieceImage);
    }

    /**
     * Updates the select image to the image at the given path
     * @param imgPath
     */
    @Override
    public void updateClickableVisual(String imgPath) {
        selectImage = loadVisual(imgPath);
    }

}
