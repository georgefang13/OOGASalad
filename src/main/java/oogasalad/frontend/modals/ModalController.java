package oogasalad.frontend.modals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.Pane;
import oogasalad.Controller.BackendObjectController;
import oogasalad.Controller.DropZoneController;
import oogasalad.Controller.FilesController;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.GraphicHandler;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.frontend.panels.ModalPanel;

/**
 * @author Han Allows the Modal to Communicate inputs and actions back with the rest of frontend
 */
public class ModalController {

  private Pane root;
  private ModalPanel parentPanel;
  private Map<String, Map<String, String>> templateMap;
  private Map<String, Component> activeComponents;
  private ComponentsFactory factory;
  private FilesController files;
  private DropZoneController dropZoneController;
  private BackendObjectController backendObjectController;
  public ModalController(ModalPanel componentPanel) {
    parentPanel = componentPanel;
    factory = new ComponentsFactory();
    templateMap = new HashMap<>();
    activeComponents = new HashMap<>();
    dropZoneController = new DropZoneController();
  }

  public void setFileController(FilesController newController){
    files = newController;
    backendObjectController = new BackendObjectController();
    backendObjectController.setGame(files.getGame());
  }

  public void createObjectTemplate(Map<String, String> map, String objectType) {
    String name = map.get("name");
    System.out.println(map.get("dropzoneID"));
    dropZoneController.setRoot(root);
    templateMap.put(name, map);
    parentPanel.addComponentTemplate(name, objectType);
  }

  public void createObjectInstance(String name, String objectType){
    objectType = objectType.substring(0, 1).toUpperCase() + objectType.substring(1);

    int firstDigitIndex = name.replaceAll("\\D", "").length() > 0 ? name.indexOf(name.replaceAll("\\D", "").charAt(0)) : -1;
    String componentTemplate = (firstDigitIndex != -1) ? name.substring(0, firstDigitIndex) : name;

    Map<String, String> map = templateMap.get(componentTemplate);
    Component c = factory.create(objectType, map);

    activeComponents.put(name, c);

    files.addComponent(c);
    dropZoneController.addDropZone(c);
    dropZoneController.addGridObject(c);
//    backendObjectController.sendOwnableObject(c);


    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
    root.getChildren().add(c.getNode());
  }

  public void editObjectInstance(Map<String, String> map, String objectType) {
    String name = map.get("name");
    objectType = objectType.substring(0, 1).toUpperCase() + objectType.substring(1);
    root.getChildren().remove(activeComponents.get(name).getNode());
    Component c = factory.create(objectType, map);
    c.setID(activeComponents.get(name).getID());
    activeComponents.put(name, c);
    GraphicHandler handler = new GraphicHandler();
    handler.moveToCenter(c);
//    backendObjectController.editOwnableObject(c);
    root.getChildren().add(c.getNode());
  }

  public void deleteObjectInstance(String name) {
    Component c = activeComponents.get(name);
//    backendObjectController.deleteOwnableObject(c);
    dropZoneController.deleteArrows(c, root);
    root.getChildren().remove(activeComponents.get(name).getNode());
  }

  public void setRoot(Pane rt) {
    root = rt;
  }

  public Component getActiveComponent(String name) {
    return activeComponents.get(name);
  }
  public Map<String, Component> getMap() {
    return activeComponents;
  }

  public void configGeneral(Map<String, String> map) {
    files = new FilesController();
    files.setGeneral(map);
    System.out.println(map.get("name"));
    files.setGameName(map.get("name"));
  }

  public List<String> dropzoneList(){
    List<String> list = dropZoneController.getValidatedDropzone();
    if(list.size() != 0) {
      list.replaceAll(value -> "DropZone" + value);
      list.set(0, "DropZone");
    }
    return list;
  }

  public FilesController getFilecontroller() {
    return files;
  }
}
