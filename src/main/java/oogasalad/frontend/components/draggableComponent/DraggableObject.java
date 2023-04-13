package oogasalad.frontend.components.draggableComponent;

import javafx.scene.Node;
import oogasalad.frontend.components.displayableComponents.DisplayableObject;

public class DraggableObject extends DisplayableObject implements DraggableComponent {
    private boolean draggable;
    private boolean active;

    public DraggableObject(int num, Node container) {
        super(num, container);
    }
    public DraggableObject(int ID) {
        super(ID);
    }
    @Override
    public void followMouse() {
        getImage().setOnMousePressed(e -> {
            double xOffset = e.getSceneX() - (getImage().getTranslateX());
            double yOffset = e.getSceneY() - (getImage().getTranslateY());
            setxOffset(xOffset);
            setyOffset(yOffset);
        });
        getImage().setOnMouseDragged(e -> {
            getImage().setTranslateX(e.getSceneX() - getxOffset());
            getImage().setTranslateY(e.getSceneY() - getyOffset());
        });
    }

    @Override
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    @Override
    public void setActiveSelected(boolean active) {
        this.active = active;

    }
}
