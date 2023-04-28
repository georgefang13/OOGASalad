package oogasalad.frontend.modals;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Pane;
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
  private ComponentsFactory factory;
  public ModalController(ComponentPanel componentPanel) {
    parentPanel = componentPanel;
    factory = new ComponentsFactory();
    componentMap = new HashMap<>();
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
    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
    root.getChildren().add(c.getNode());
  }
  public void setRoot(Pane rt) {
    root = rt;
  }
}
