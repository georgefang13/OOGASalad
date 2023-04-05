package oogasalad.frontend.components.Components.GameObjectComponent;

import java.util.List;
import javafx.scene.Node;

/**
 * @author Han
 * Concrete Class for GameObject, a reflection of what is going to be a "GameObject" on the backend
 */
public class GameObject implements GameObjectComponent{


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
  public void setVisible(boolean visible) {

  }

  @Override
  public void setZIndex(int zIndex) {

  }

  @Override
  public void setView(String imagePath) {

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

  @Override
  public void setPos(int position) {

  }

  @Override
  public void setName(String name) {

  }

  @Override
  public List<Node> getChildren() {
    return null;
  }

  @Override
  public void setPlayable(boolean playable) {

  }
}
