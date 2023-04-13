package oogasalad.frontend.components.gameObjectComponent;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.Point;
import oogasalad.frontend.components.draggableComponent.DraggableObject;


/**
 * @author Han, Aryan
 * Concrete Class for GameObject, a reflection of what is going to be a "GameObject" on the backend
 */
public class GameObject extends DraggableObject implements GameObjectComponent{
  private String name;
  private List<Node> children;
  private boolean playable;
  private final String DEFAULT_FILE_PATH = "frontend.properties.Defaults.GameObject";
  private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

  public GameObject(int ID){
    super(ID);
    children = null;
    Image newImage = new Image(DEFAULT_BUNDLE.getString("DEFAULT_IMAGE"));
    setImage(DEFAULT_BUNDLE.getString("DEFAULT_IMAGE"));
    followMouse();
  }
  public GameObject(int ID, Node container){
    super(ID, container);
  }

  @Override
  public void setName(String newName) {
    name = newName;
  }

  @Override
  public List<Node> getChildren() {
    return children;
  }

  @Override
  public void setPlayable(boolean play) {
    playable = play;
  }
  @Override
  public Node getNode(){
    return getImage();
  }
}
