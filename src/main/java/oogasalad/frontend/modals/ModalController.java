package oogasalad.frontend.modals;

import java.util.Map;
import javafx.scene.layout.Pane;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.GraphicHandler;
import oogasalad.frontend.panels.subPanels.ComponentPanel;

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
  }

  public void createGameObjectTemplate(Map<String, String> map) {
    String name = map.get("name");
    componentMap.put(name, map);
    parentPanel.addGameComponentTemplate(name);
  }

  public void createGameObjectInstance(String name){
    Map<String, String> map = componentMap.get(name);
    Component c = factory.create("GameObject", map);
    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
    root.getChildren().add(c.getNode());
  }
  public void setRoot(Pane rt) {
    root = rt;
  }
}
