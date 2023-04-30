package oogasalad.frontend.modals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.layout.Pane;
import oogasalad.Controller.FilesController;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.GraphicHandler;
import oogasalad.frontend.panels.editorPanels.ComponentPanel;

/**
 * @author Han Allows the Modal to Communicate inputs and actions back with the rest of frontend
 */
public class ModalController {

  private Pane root;
  private ComponentPanel parentPanel;
  private Map<String, Map<String, String>> componentMap;
  private Map<String, Component> allComponents;
  private ComponentsFactory factory;
  private FilesController files;
  public ModalController(ComponentPanel componentPanel) {
    parentPanel = componentPanel;
    factory = new ComponentsFactory();
    componentMap = new HashMap<>();
    allComponents = new HashMap<>();
  }

  public void setFileController(FilesController newController){
    files = newController;
  }
  public void createObjectTemplate(Map<String, String> map, String objectType) {
    String name = map.get("name");
    componentMap.put(name, map);
    parentPanel.addComponentTemplate(name, objectType);
  }

  public void createObjectInstance(String name, String objectType){
    objectType = objectType.substring(0, 1).toUpperCase() + objectType.substring(1);
    System.out.println(objectType);
    Map<String, String> map = componentMap.get(name);
    Component c = factory.create(objectType, map);
    files.addComponent(c);
    allComponents.put(name, c);
    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
    root.getChildren().add(c.getNode());
  }

  public void deleteObjectInstance(String name) {
    root.getChildren().remove(allComponents.get(name).getNode());
  }

  public void setRoot(Pane rt) {
    root = rt;
  }

}
