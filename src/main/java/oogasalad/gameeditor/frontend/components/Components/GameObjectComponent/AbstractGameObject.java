package oogasalad.gameeditor.frontend.components.Components.GameObjectComponent;

import javafx.scene.Node;
import oogasalad.gameeditor.frontend.components.Components.AbstractComponent;

public abstract class AbstractGameObject extends AbstractComponent {

  public AbstractGameObject(int num, Node container) {
    super(num, container);
  }

  public abstract void followMouse();

  public abstract void setDraggable(boolean drag);
}
