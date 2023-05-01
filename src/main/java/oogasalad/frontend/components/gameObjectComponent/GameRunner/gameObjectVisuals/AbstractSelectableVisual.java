package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import oogasalad.frontend.managers.DisplayManager;
/**
 * @author Owen MacKenzie
 */
public abstract class AbstractSelectableVisual extends HBox implements SelectableVisual {
    protected String objectID;
    protected int width;
    protected int height;

    /**
     * Constructor for AbstractSelectableVisual
     * @param id
     * @param width
     * @param height
     */
    public AbstractSelectableVisual(String id, int width, int height) {
        super();
        objectID = id;
        this.width = width;
        this.height = height;

    }

    /**
     * Returns the objectID of the visual
     * @return String
     */
    @Override
    public String getObjectID(){
        return objectID;
    }

    public record SelectableVisualParams(boolean hasSelectImage, String param){}
    protected void switchImages(Node oldImage, Node newImage){
        if (this.getChildren().contains(oldImage)) {
            this.getChildren().remove(oldImage);
            this.getChildren().add(newImage);
        }
    }
    protected Node loadVisual(String imgPath){
        return DisplayManager.loadImage(imgPath,width,height);
    }
    protected Color loadColorFromHex(String hexColor){
        return Color.web(hexColor);
    }
}
