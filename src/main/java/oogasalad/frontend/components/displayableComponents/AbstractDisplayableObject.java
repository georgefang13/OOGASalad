package oogasalad.frontend.components.displayableComponents;

import javafx.scene.Node;
import oogasalad.frontend.components.AbstractComponent;

public class AbstractDisplayableObject extends AbstractComponent {
    public AbstractDisplayableObject(int num, Node container) {
        super(num, container);
    }
    public AbstractDisplayableObject(int ID) {
        super(ID);
    }
}
