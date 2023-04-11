package oogasalad.frontend.components.logicComponent;

import java.util.List;
import javafx.scene.Node;
import oogasalad.frontend.components.draggableComponent.DraggableObject;

/**
 * @author Han, Aryan
 * Concrete Class of a LogicBlocks
 */

public class LogicObject extends DraggableObject implements LogicComponent {

  private List<LogicComponent> children;
  private LogicComponent before;
  private LogicComponent after;
  public LogicObject(int num, Node container) {
    super(num, container);
  }

  @Override
  public List<LogicComponent> getChildren() {
    return children;
  }

  @Override
  public LogicComponent getBefore() {
    return before;
  }

  @Override
  public LogicComponent getAfter() {
    return after;
  }

  @Override
  public String getParams() {
    return null;
  }
}
