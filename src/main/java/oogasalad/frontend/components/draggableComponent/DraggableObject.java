package oogasalad.frontend.components.draggableComponent;

import javafx.scene.Node;

public class DraggableObject extends AbstractDraggableObject implements DraggableComponent {
    public DraggableObject(int num, Node container) {
        super(num, container);
    }
    public DraggableObject(int ID) {
        super(ID);
    }

    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setID(int id) {

    }

    @Override
    public void followMouse() {

    }

    @Override
    public void setDraggable(boolean draggable) {

    }

    @Override
    public void setActiveSelected(boolean active) {

    }
}
