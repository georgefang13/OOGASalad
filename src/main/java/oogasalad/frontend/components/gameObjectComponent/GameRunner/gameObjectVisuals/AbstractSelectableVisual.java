package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import oogasalad.frontend.managers.DisplayManager;

public abstract class AbstractSelectableVisual extends HBox implements SelectableVisual {
    protected String objectID;
    protected int width;
    protected int height;
    public AbstractSelectableVisual(String id, int width, int height) {
        super();
        objectID = id;
        this.width = width;
        this.height = height;

    }
    @Override
    public String getObjectID(){
        return objectID;
    }
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
