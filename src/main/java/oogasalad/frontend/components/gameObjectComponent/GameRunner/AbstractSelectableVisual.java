package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.layout.HBox;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.SelectableVisual;

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
}
