package oogasalad.frontend.components;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.stage.Stage;

/**
 * @author hanzh This ComponentFactory is meant to provide a Factory to easily instantiate different
 * types of Components given any input parameters
 */
public class ComponentsFactory {

  private final String FACTOR_PROPERTIES = "frontend.properties.permanentText.ComponentsFactory";
  private ResourceBundle bundle;
  private int ID;
  private Stage stage;
  private Component component;
  public ComponentsFactory(){
    ID = 0;
    bundle = ResourceBundle.getBundle(FACTOR_PROPERTIES);
  }

  /**
   * Use Reflection to determine the Component type and construct the proper type of Component This
   * is for the default Component
   *
   * @param type type of component being created. If it's an invalid Component, an error is thrown.
   * @return the Component the client wanted
   */
  public Component create(String type){
    String lowercase = type.substring(0, 1).toLowerCase() + type.substring(1);
    try{
      Class<?> c = Class.forName(bundle.getString("package") + lowercase + "Component." + type);
      System.out.println(c);
      Constructor<?> constructor = c.getConstructor(String.class);
      System.out.println(constructor);
      component = (Component) constructor.newInstance(Integer.toString(ID));
      ID++;
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }
    return component;
  }

  /**
   * Takes in Inputs of parameters to create a Component type. This can either establish the default
   * Component or create the input
   */
  public Component create(String type, ArrayList<String> params){
    Map<String, String> map = new HashMap<>();

    for (String s : params) {
      String[] parts = s.split(bundle.getString("SplitCharacter"));
      map.put(parts[0], parts[1]);
    }
    try {
      Class<?> c = Class.forName(type);
      Constructor<?> constructor = c.getConstructor(map.getClass());
      component = (Component) constructor.newInstance(Integer.toString(ID), map);
      ID++;
    } catch (Exception e){
      System.out.println("Failed");
    }
    return component;
  }
  public Component create(String type, Map<String, String> map){
    String lowercase = type.substring(0, 1).toLowerCase() + type.substring(1);
    try{
      Class<?> c = Class.forName(bundle.getString("package")+lowercase + "Component." + type);
      Constructor<?> constructor = c.getConstructor(String.class, Map.class);
      component = (Component) constructor.newInstance(Integer.toString(ID), map);
      ID++;
    } catch (Exception e){
      e.printStackTrace();
    }
    return component;
  }

  public int getID() {
    return ID;
  }

}
