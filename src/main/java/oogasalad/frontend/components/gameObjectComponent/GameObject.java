package oogasalad.frontend.components.gameObjectComponent;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.managers.DisplayManager;

import java.util.List;
import java.util.Map;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;


/**
 * @author Han, Aryan Concrete Class for GameObject, a reflection of what is going to be a
 * "GameObject" on the backend
 */
public class GameObject extends AbstractComponent implements GameObjectComponent{
  private List<Node> children;
  private ImageView image;
  private Dropzone zone;
  private String name;
  private int width;
  private int height;
  private double rotate;
  private double dropzoneID;

  public GameObject(String ID) {
    super(ID);
    children = null;
    instantiatePropFile("frontend.properties.Defaults.GameObject");
  }

  public GameObject(String ID, Map<String, String> map){
    super(ID);
    children = null;
    setDraggable(true);
    instantiatePropFile("frontend.properties.Defaults.GameObject");
    setValuesfromMap(map);
    initialize();
    followMouse();
  }

  private void initialize() {
    image.setFitWidth(width);
    image.setFitHeight(height);
    image.setRotate(rotate);
  }

  @Override
  public void setImage(String imagePath) {
    image = (ImageView) DisplayManager.loadImage(imagePath,getHeight(),getWidth());
  }

  @Override
  public ImageView getImage() {
    return image;
  }

  @Override
  public List<Node> getChildren() {
    return children;
  }

  @Override
  public Node getNode() {
    return getImage();
  }
}
