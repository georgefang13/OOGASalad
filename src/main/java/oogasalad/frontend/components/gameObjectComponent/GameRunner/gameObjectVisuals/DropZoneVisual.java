package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
/**
 * @author Owen MacKenzie
 */
public class DropZoneVisual extends AbstractSelectableVisual {
    private Node unselectedVisual;
    private Node selectedVisual;

    /**
     * Constructor for the drop zone visual
     * @param dropZoneImage
     * @param selectedDropZoneImage
     * @param width
     * @param height
     * @param x
     * @param y
     * @param id
     */
    public DropZoneVisual(Node dropZoneImage,Node selectedDropZoneImage, int width, int height, double x, double y,String id) {
        super(id,width,height);
        unselectedVisual = dropZoneImage;
        selectedVisual = selectedDropZoneImage;
        initHBox(width,height,x,y);
        this.getChildren().add(unselectedVisual);
    }
    /**
     * for putting drop zone in an HBOX
     */
    private void initHBox(double width, double height, double x, double y){
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setMaxHeight(height);
        this.setMaxWidth(width);
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    /**
     * Switches the images of clickable drop zone
     */
    @Override
    public void showClickable() {
        switchImages(unselectedVisual,selectedVisual);
    }

    /**
     * Switches the images of unclickable drop zone
     */
    @Override
    public void showUnclickable() {
        switchImages(selectedVisual,unselectedVisual);
    }

    /**
     * Updates the image of the drop zone
     * @param imgPath
     */
    @Override
    public void updateClickableVisual(String imgPath) {
        selectedVisual = loadVisual(imgPath);
    }
}
