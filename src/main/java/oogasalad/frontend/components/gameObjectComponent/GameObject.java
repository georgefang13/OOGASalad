package oogasalad.frontend.components.gameObjectComponent;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ConversionContext;
import oogasalad.frontend.components.ParamFactory;
import oogasalad.frontend.components.Point;


/**
 * @author Han, Aryan Concrete Class for GameObject, a reflection of what is going to be a
 * "GameObject" on the backend
 */
public class GameObject extends AbstractComponent implements GameObjectComponent{

  private String name;
  private List<Node> children;
  private boolean playable;
  private ImageView image;

  public GameObject(int ID) {
    super(ID);
    children = null;
    instantiatePropFile("frontend.properties.Defaults.GameObject");
    this.setDefault();
    this.followMouse();
    this.getNode();
  }

  public GameObject(int ID, Map<String, String> map){
    super(ID);
    children = null;
    setImage(DEFAULT_BUNDLE.getString(replaceWithFileLoadingByID()));
    for(String param: map.keySet()){
      try{
        Field field = this.getClass().getDeclaredField(param);
        field.setAccessible(true);
        Class<?> fieldType = field.getType();
        ConversionContext<?> conversionContext = ParamFactory.createConversionContext(fieldType);
        Object value = conversionContext.convert(map.get(param));
        field.set(this, value);
      } catch (Exception e){
        e.printStackTrace();
      }
    }
    followMouse();
  }

  private String replaceWithFileLoadingByID(){
    if (ID < 6){
      return "DEFAULT_IMAGE";
    }
    else {
      return "X_IMAGE";
    }
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
  public void setName(String newName) {
    name = newName;
  }
  public String getName(){
    return name;
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
  public Node getNode() {
    return getImage();
  }

  @Override
  public void setDefault() {
    Image newImage = new Image(getDEFAULT_BUNDLE().getString("DEFAULT_IMAGE"));
    setImage(getDEFAULT_BUNDLE().getString("DEFAULT_IMAGE"));
  }

}
