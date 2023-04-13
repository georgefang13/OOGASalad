package oogasalad.frontend.components.gameObjectComponent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.Point;
import oogasalad.frontend.components.displayableComponents.DisplayableComponent;
import oogasalad.frontend.components.displayableComponents.DisplayableObject;


/**
 * @author Han, Aryan
 * Concrete Class for GameObject, a reflection of what is going to be a "GameObject" on the backend
 */

public class GameObject extends DisplayableObject implements GameObjectComponent, DisplayableComponent, Component {
  private String name;
  private List<Node> children;
  private boolean playable;

  public GameObject(int ID){
    super(ID);
    children = null;
    followMouse();
  }

  @Override
  public void setDefault() {
    Properties properties = new Properties();
    try (InputStream inputStream = getClass().getResourceAsStream(DEFAULT_FILE_PATH)) {
      properties.load(inputStream);
      setVisibleBool(Boolean.valueOf(properties.getProperty("VISIBLE")));
      setZIndex(Integer.parseInt(properties.getProperty("Z_INDEX")));
      setSize(Integer.parseInt(properties.getProperty("SIZE")));
      getImage().setImage(new Image(properties.getProperty("DEFAULT_IMAGE")));
    } catch (IOException e) {
      System.out.println("Failed");
    }
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


}
