package oogasalad.gameeditor.frontend.ViewObjects.Components;

/**
 * @author hanzh
 * This ComponentFactory is meant to provide a Factory to easily instantiate different types of Components
 * given any input parameters
 */
public class ComponentsFactory {
  public ComponentsFactory(){

  }
  /**
   * Use Reflection to determine the Component type and construct the proper type of Component
   * @param type type of component being created. If it's an invalid Component, an error is thrown.
   * @return the Component the client wanted
   */
  public Component create(String type){
    //TODO properly cite Chat GPT here
    Component component = null;
    try{
      Class<?> c = Class.forName(type + "Component");
      component = (Component) c.getDeclaredConstructor().newInstance();
    } catch (Exception e){
      //TODO add logging code
      System.out.println("Failed");
    }
    return component;
  }

}
