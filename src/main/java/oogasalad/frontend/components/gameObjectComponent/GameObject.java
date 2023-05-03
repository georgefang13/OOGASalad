package oogasalad.frontend.components.gameObjectComponent;

import javafx.geometry.Point2D;
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
  private String dropzoneID;
  private double x;
  private double y;
  private Boolean unselectedHasImage;
  private Boolean selectedHasImage;
  private String unselectedparam;
  private String selectedparam;

  /**
   * Constructor for GameObject
   * @param ID
   */
  public GameObject(String ID) {
    super(ID);
    children = null;
    instantiatePropFile("frontend.properties.Defaults.GameObject");
  }

  /**
   * Constructor for GameObject
   * @param ID
   * @param map
   */
  public GameObject(String ID, Map<String, String> map){
    super(ID);
    children = null;
    setDraggable(true);
    instantiatePropFile("frontend.properties.Defaults.GameObject");
    setValuesfromMap(map);
    initialize();
    followMouse();
  }

  /**
   * Initializes object
   */
  private void initialize() {
    image.setFitWidth(width);
    image.setFitHeight(height);
    image.setRotate(rotate);
  }

  /**
   * sets image of the object
   * @param imagePath the path where the image is contained to represent the Component
   */
  @Override
  public void setImage(String imagePath) {
    image = (ImageView) DisplayManager.loadImage(imagePath,getHeight(),getWidth());
  }

  /**
   * get the image of the object
   * @return
   */
  @Override
  public ImageView getImage() {
    return image;
  }

  /**
   * gets the children of the object
   * @return
   */
  @Override
  public List<Node> getChildren() {
    return children;
  }

  /**
   * gets node of the image
   * @return
   */
  @Override
  public Node getNode() {
    return getImage();
  }
}
