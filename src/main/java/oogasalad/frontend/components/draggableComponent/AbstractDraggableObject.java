package oogasalad.frontend.components.draggableComponent;

import javafx.scene.Node;
import oogasalad.frontend.components.AbstractComponent;

public abstract class AbstractDraggableObject extends AbstractComponent {
    public AbstractDraggableObject(int num, Node container) {
        super(num, container);
    }

    public AbstractDraggableObject(int ID) {
        super(ID);
    }
}
