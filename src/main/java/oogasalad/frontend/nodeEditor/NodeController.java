package oogasalad.frontend.nodeEditor;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.windows.NodeWindow;

public class NodeController {

  private NodeScene scene;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();

  public NodeController(NodeWindow nodeWindow) {
    scene = new NodeScene(this);
    Stage stage = new Stage();
    stage.setScene(scene.getScene());
    stage.show();
    stage.setMaxHeight(propertyManager.getNumeric("WindowHeight"));
    stage.setMaxWidth(propertyManager.getNumeric("WindowWidth"));
    stage.setResizable(false);
    //nodeWindow.showScene(scene);
  }

  public NodeController() {
    scene = new NodeScene(this);
  }


  public void openAndSwitchToTab(String state, String action) {
    scene.openAndSwitchToTab(state, action);
  }

  public void saveUserCodeFiles(String filePath) {
    scene.saveAllContent(filePath);
  }

  public void loadAllContent(String filePath) {
    scene.loadAllContent(filePath);
  }

  public Scene getScene() {
    return scene.getScene();
  }

  public void saveInterpreterFiles(String userCodeFilesPath) {
  }
}
