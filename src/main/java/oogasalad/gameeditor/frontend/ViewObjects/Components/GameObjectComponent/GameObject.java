package oogasalad.gameeditor.frontend.ViewObjects.Components.GameObjectComponent;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.gameeditor.frontend.ViewObjects.Components.Point;


/**
 * @author Han
 * Concrete Class for GameObject, a reflection of what is going to be a "GameObject" on the backend
 */
public class GameObject extends AbstractGameObject implements GameObjectComponent{

  ImageView image;
  double size;
  String name;
  int ID;
  boolean visible;
  boolean draggable;
  boolean active;
  List<Node> children;
  boolean playable;
  Point absolute;
  Point editor;
  double xOffset;
  double yOffset;

  public GameObject(int num, Node container) {
    super(num, container);
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
