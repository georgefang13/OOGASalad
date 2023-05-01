package oogasalad.frontend.nodeEditor;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.scenes.SceneController;
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

  public void saveInterpreterFiles(String userCodeFilesPath) {
    scene.saveAllContent("");
  }

  /**
   * Opens a new tab with the given state and action
   *
   * @return void
   */
  public void openAndSwitchToTab(CodeEditorTab panel) {
    scene.openAndSwitchToTab(panel);
  }

  /**
   * Saves all of the content in the current tab to the given file path
   *
   * @param filePath
   * @return void
   */
  public void saveAllContent(String filePath) {
    scene.saveAllContent(filePath);
  }

  /**
   * Loads all of the content in the current tab from the given file path
   *
   * @param filePath
   */
  public void loadAllContent(String filePath) {
    scene.loadAllContent(filePath);
  }

  /**
   * Returns the scene that is being used by the NodeController
   *
   * @return
   */
  public Scene getScene() {
    return scene.getScene();
  }
}
