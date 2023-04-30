package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public abstract class AbstractSelectableVisual extends HBox implements SelectableVisual {
    protected String objectID;
    public AbstractSelectableVisual(String id) {
        super();
        objectID = id;
    }
    @Override
    public String getObjectID(){
        return objectID;
    }
    protected void switchImages(Node oldImage, Node newImage){
        this.getChildren().remove(oldImage);
        this.getChildren().add(newImage);
    }
}
