package oogasalad.frontend.components.gameObjectComponent;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.AbstractComponent;


/**
 * @author Han, Aryan Concrete Class for GameObject, a reflection of what is going to be a
 * "GameObject" on the backend
 */
public class GameObject extends AbstractComponent implements GameObjectComponent{

  private String name;
  private List<Node> children;

  private ImageView image;
  private double width;
  private double height;
  private double rotate;

  public GameObject(String ID) {
    super(ID);
    children = null;
    instantiatePropFile("frontend.properties.Defaults.GameObject");
    //followMouse();
  }

  public GameObject(String ID, Map<String, String> map){
    super(ID);
    children = null;
    setDraggable(true);
    instantiatePropFile("frontend.properties.Defaults.GameObject");
    //setImage(getDEFAULT_BUNDLE().getString(replaceWithFileLoadingByID()));
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
    Image newImage;
    try {
      newImage = new Image(new FileInputStream(imagePath));
    } catch (Exception e) {
      System.out.println("Image " + imagePath + " not found");
      return;
    }
    image = new ImageView(newImage);
    image.setFitWidth(size);
    image.setFitHeight(size);
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
