package oogasalad.frontend.components.gameObjectComponent;

import javafx.scene.Node;
import oogasalad.frontend.components.AbstractComponent;

public abstract class AbstractGameObject extends AbstractComponent {

  public AbstractGameObject(int num, Node container) {
    super(num, container);
  }

  public AbstractGameObject(int ID) {
    super(ID);
  }

  public abstract void followMouse();

  public abstract void setDraggable(boolean drag);
}
