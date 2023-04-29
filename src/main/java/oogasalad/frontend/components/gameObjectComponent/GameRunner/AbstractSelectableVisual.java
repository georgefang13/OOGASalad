package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public abstract class AbstractSelectableVisual extends HBox implements SelectableVisual{
    protected String objectID;
    protected GameRunnerObject gameRunnerObject;
    public AbstractSelectableVisual(String id) {
        super();
        objectID = id;
    }
    @Override
    public String getObjectID(){
        return objectID;
    }
}
