package oogasalad.frontend.components.displayableComponents;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.Point;

/**
 * @author Aryan, Han Concrete Class for DisplayableObject
 */

public class DisplayableObject extends AbstractComponent implements DisplayableComponent {

  private boolean visible;
  private int zIndex;
  private int size;
  private ImageView image;
  private Point absolute;
  private Point editor;
  private double xOffset;
  private double yOffset;
  private final String DEFAULT_FILE_PATH = "frontend/properties/Defaults/GameObject";
  private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

  public DisplayableObject(int num, Node container) {
    super(num, container);
    Image newImage = new Image(DEFAULT_BUNDLE.getString("DEFAULT_IMAGE"));
    image.setImage(newImage);
  }

  public DisplayableObject(int ID) {
    super(ID);
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
      xOffset = Integer.parseInt(properties.getProperty("X_OFFSET"));
      yOffset = Integer.parseInt(properties.getProperty("Y_OFFSET"));
      absolute = new Point(xOffset, yOffset);
    } catch (IOException e) {
      System.out.println("Failed");
    }
  }

  @Override
  public void followMouse() {
    image.setOnMousePressed(e -> {
      double xOffset = e.getSceneX() - (getImage().getTranslateX()
          - getImage().getBoundsInLocal().getWidth() / 2);
      double yOffset = e.getSceneY() - (getImage().getTranslateY()
          - getImage().getBoundsInLocal().getHeight() / 2);
      setxOffset(xOffset);
      setyOffset(yOffset);
    });
    getImage().setOnMouseDragged(e -> {
      getImage().setTranslateX(e.getSceneX() - getxOffset());
      getImage().setTranslateY(e.getSceneY() - getyOffset());
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

  @Override
  public double getxOffset() {
    return xOffset;
  }

  @Override
  public double getyOffset() {
    return yOffset;
  }

  @Override
  public void setyOffset(double yOffset) {
    this.yOffset = yOffset;
  }

  @Override
  public void setxOffset(double xOffset) {
    this.xOffset = xOffset;
  }

  protected void setVisibleBool(boolean vis) {
    visible = vis;
  }

  protected void setzIndex(int z) {
    zIndex = z;
  }

  protected void setAbsolutePoint(Point abs) {
    absolute = abs;
  }

  protected void setEditorPoint(Point ed) {
    editor = ed;
  }
}
