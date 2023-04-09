package oogasalad.frontend.components.logicComponent;

import java.util.List;
import javafx.scene.Node;
import oogasalad.frontend.components.draggableComponent.DraggableObject;

/**
 * @author Han, Aryan
 * Concrete Class of a LogicBlocks
 */

public class LogicObject extends DraggableObject implements LogicComponent {

  public LogicObject(int num, Node container) {
    super(num, container);
  }

  @Override
  public List<LogicComponent> getChildren() {
    return null;
  }

  @Override
  public LogicComponent getBefore() {
    return null;
  }

  @Override
  public LogicComponent getAfter() {
    return null;
  }

  @Override
  public String getParams() {
    return null;
  }
}
