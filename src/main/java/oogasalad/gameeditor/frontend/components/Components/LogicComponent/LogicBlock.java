package oogasalad.gameeditor.frontend.components.Components.LogicComponent;

import java.util.List;
import javafx.scene.Node;

/**
 * @author Han
 * Concrete Class of a LogicBLocks
 */
public class LogicBlock implements LogicComponent{

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

  @Override
  public void setVisible(boolean visible) {

  }

  @Override
  public void setZIndex(int zIndex) {

  }

  @Override
  public void setImage(String imagePath) {

  }

  @Override
  public void setSize(int size) {

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
