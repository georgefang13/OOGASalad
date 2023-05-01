package oogasalad.frontend.nodeEditor;

import javafx.scene.Scene;
import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.windows.NodeWindow;

import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

public class NodeController {

  private NodeScene scene;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();
  private static final MainLogger logger = MainLogger.getInstance(NodeController.class);

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
  }

  /**
   * Opens a new tab with the given state and action
   *
   * @param state
   * @param action
   * @return void
   */
  public void openAndSwitchToTab(String state, String action) {
    scene.openAndSwitchToTab(state, action);
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
    logger.debug("retrieved the scence");
    return scene.getScene();
  }
}
