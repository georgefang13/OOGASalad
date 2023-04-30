package oogasalad.frontend.modals;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.layout.Pane;
import oogasalad.Controller.DropZoneController;
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
  private Map<String, Map<String, String>> templateMap;
  private Map<String, Component> activeComponents;
  private ComponentsFactory factory;
  private FilesController files;
  private DropZoneController dropZoneController;
  public ModalController(ComponentPanel componentPanel) {
    parentPanel = componentPanel;
    factory = new ComponentsFactory();
    templateMap = new HashMap<>();
    activeComponents = new HashMap<>();
    dropZoneController = new DropZoneController();
  }

  public void setFileController(FilesController newController){
    files = newController;
  }

  public void createObjectTemplate(Map<String, String> map, String objectType) {
    String name = map.get("name");
    dropZoneController.setRoot(root);
    templateMap.put(name, map);
    parentPanel.addComponentTemplate(name, objectType);
  }

  public void createObjectInstance(String name, String objectType){
    objectType = objectType.substring(0, 1).toUpperCase() + objectType.substring(1);
    Map<String, String> map = templateMap.get(name);
    Component c = factory.create(objectType, map);

//      for (String key : activeComponents.keySet()) {
//          if (key.startsWith(name)) {
//              String append = Integer.toString(activeComponents.keySet().size());
//              activeComponents.put(name+append, c);
//          } else {
//              activeComponents.put(name, c);
//          }
//      }

   activeComponents.put(name, c);

    files.addComponent(c);
    dropZoneController.addDropZone(c);
    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
    root.getChildren().add(c.getNode());
  }

  public void editObjectInstance(Map<String, String> map, String objectType) {
    String name = map.get("name");
    objectType = objectType.substring(0, 1).toUpperCase() + objectType.substring(1);
    root.getChildren().remove(activeComponents.get(name).getNode());
    Component c = factory.create(objectType, map);
    activeComponents.put(name, c);
    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
    root.getChildren().add(c.getNode());
  }

  public void deleteObjectInstance(String name) {
    root.getChildren().remove(activeComponents.get(name).getNode());
  }

  public void setRoot(Pane rt) {
    root = rt;
  }

  public Component getActiveComponent(String name) {
    return activeComponents.get(name);
  }

}
