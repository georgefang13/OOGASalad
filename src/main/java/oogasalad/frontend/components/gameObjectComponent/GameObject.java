package oogasalad.frontend.components.gameObjectComponent;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.Point;


/**
 * @author Han
 * Concrete Class for GameObject, a reflection of what is going to be a "GameObject" on the backend
 */
public class GameObject extends AbstractGameObject implements GameObjectComponent{

  private final String DEFAULT_FILE_PATH = "frontend/properties/Defaults/GameObject.properties";
  final private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);
  private ImageView image;
  private double size;
  private String name;
  private boolean visible;
  private boolean draggable;
  private boolean active;
  private List<Node> children;
  private boolean playable;
  private Point absolute;
  private Point editor;
  private double xOffset;
  private double yOffset;

  public GameObject(int ID){
    super(ID);
    Image newImage = new Image(DEFAULT_BUNDLE.getString("DEFAULT_IMAGE"));
    image.setImage(newImage);

    children = null;

  }
  public GameObject(int ID, Node container){
    super(ID, container);
  }
  @Override
  public Node getNode() {
    return image;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public void setID(int id) {
    ID = id;
  }

  @Override
  public void setVisible(boolean vis) {
    visible = vis;
  }

  @Override
  public void setZIndex(int zIndex) {
    image.setTranslateZ(zIndex);
    absolute.setZ(zIndex);
    editor.setZ(zIndex);
  }

  @Override
  public void setImage(String imagePath) {
    Image newImage = new Image(imagePath);
    image.setImage(newImage);
  }

  @Override
  public void setSize(int newSize) {
    size = newSize;
    image.setScaleX(size);
    image.setScaleY(size);
  }

  /**
   * https://shareg.pt/zWkIH8c
   */
  @Override
  public void followMouse() {
    image.setOnMousePressed(e -> {
      xOffset = e.getSceneX() - (image.getTranslateX() - image.getBoundsInLocal().getWidth()/2);
      yOffset = e.getSceneY() - (image.getTranslateY() - image.getBoundsInLocal().getHeight()/2);
    });
    image.setOnMouseDragged(e -> {
      image.setTranslateX(e.getSceneX() - xOffset);
      image.setTranslateY(e.getSceneY() - yOffset);
    });
  }

  @Override
  public void setDraggable(boolean drag) {
    draggable = drag;
  }

  @Override
  public void setActiveSelected(boolean act) {
    active = act;
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

}
