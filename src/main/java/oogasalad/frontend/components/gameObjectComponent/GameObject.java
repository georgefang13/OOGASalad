package oogasalad.frontend.components.gameObjectComponent;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.Point;


/**
 * @author Han, Aryan
 * Concrete Class for GameObject, a reflection of what is going to be a "GameObject" on the backend
 */
public class GameObject extends AbstractComponent implements GameObjectComponent{
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

  //TODO fix default values for map constructor
  public GameObject(int ID, Map<String, String> map){
    super(ID);
    children = null;
    setImage(DEFAULT_BUNDLE.getString("DEFAULT_IMAGE"));
    followMouse();
    for(String param: map.keySet()){
      try{
        Field field = getClass().getDeclaredField(param);
        field.setAccessible(true);
        field.set(this, map.get(param));
      } catch (Exception e){
        System.out.println("Test");
      }
    }
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
