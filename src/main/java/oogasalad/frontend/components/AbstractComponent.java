package oogasalad.frontend.components;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Han and Aryan
 * AbstractComponent is the abstraction that all Components are built off of.
 */
public class AbstractComponent implements Component {

  protected int ID;
  protected Node node;
  private boolean draggable;
  private boolean active;
  private boolean visible;
  private int zIndex;
  private int size;
  private ImageView image;
  private Point absolute;
  private Point editor;
  private final String DEFAULT_FILE_PATH = "frontend/properties/Defaults/GameObject";
  private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

  public AbstractComponent(int id) {
    ID = id;
  }
  public AbstractComponent(int num, Node container){
    ID = num;
    node = container;
  }

  @Override
  public Node getNode() {
    return node;
  }

  @Override
  public void setNode(Node node) {
    this.node = node;
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
  public void setDraggable(boolean draggable) {
    this.draggable = draggable;
  }

  @Override
  public void setActiveSelected(boolean active) {
    this.active = active;
  }

  @Override
  public void setDefault() {
    Properties properties = new Properties();
    try (InputStream inputStream = getClass().getResourceAsStream(DEFAULT_FILE_PATH)) {
      properties.load(inputStream);
      visible = Boolean.valueOf(properties.getProperty("VISIBLE"));
      zIndex = Integer.parseInt(properties.getProperty("Z_INDEX"));
      size = Integer.parseInt(properties.getProperty("SIZE"));
      image.setImage(new Image(properties.getProperty("DEFAULT_IMAGE")));
    } catch (IOException e) {
      System.out.println("Failed");
    }
  }

  @Override
  public void followMouse() {
    image.setOnMousePressed(e -> {
      double xOffset = e.getSceneX() - (getImage().getTranslateX() - getImage().getBoundsInLocal().getWidth()/2);
      double yOffset = e.getSceneY() - (getImage().getTranslateY() - getImage().getBoundsInLocal().getHeight()/2);
    });
    getImage().setOnMouseDragged(e -> {
      getImage().setTranslateX(e.getSceneX());
      getImage().setTranslateY(e.getSceneY());
    });
  }

  @Override
  public void setVisible(boolean visible) {
    this.visible = visible;
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
    image = new ImageView(newImage);
  }

  @Override
  public ImageView getImage() {
    return image;
  }

  @Override
  public void setSize(int size) {
    this.size = size;
    image.setScaleX(size);
    image.setScaleY(size);
  }

  protected void setVisibleBool(boolean vis){
    visible = vis;
  }
  protected void setzIndex(int z){
    zIndex = z;
  }
  protected void setAbsolutePoint(Point abs){
    absolute = abs;
  }
  protected void setEditorPoint(Point ed){
    editor = ed;
  }
}
