package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import oogasalad.frontend.managers.DisplayManager;

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
        if (this.getChildren().contains(oldImage)) {
            this.getChildren().remove(oldImage);
            this.getChildren().add(newImage);
        }
    }
}
