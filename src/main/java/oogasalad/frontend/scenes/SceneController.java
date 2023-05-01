package oogasalad.frontend.scenes;

import oogasalad.Controller.FilesController;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.GameEditorWindow;
import oogasalad.frontend.windows.WindowMediator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

public class SceneController implements SceneMediator{

  private static final MainLogger logger = MainLogger.getInstance(SceneController.class);

  private static final String MAIN_ID = "main";
  private AbstractScene currentScene;
  private Map<String, AbstractScene> scenes;
  private String windowID;
  private WindowMediator windowController;
  private FilesController filesController;
  private Stack<Object> sceneData = new Stack<>();
  public SceneController(String windowID, WindowMediator windowController) {
    this.windowID = windowID;
    this.windowController = windowController;
    scenes = new HashMap<>();
    filesController = new FilesController("Test");

    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
  }

  public void switchToScene(String sceneID) {
    currentScene = scenes.get(sceneID);
    getWindow().showScene(currentScene); //TODO: USE OBSERVER PATTERN TO DO THIS
    logger.trace(String.format("Switched to a scence: ID - %s", sceneID));
  }

  public void addAndLinkScene(SceneTypes sceneType, String sceneID) {
    if (!scenes.containsKey(sceneID)) {
      AbstractScene newScene = getWindow().addNewScene(sceneType);
      scenes.put(sceneID, newScene);
      logger.trace(String.format("addAndLinkScene: ID - %s", sceneID));
    }
  }

  public void setDefaultScene(SceneTypes defaultSceneType) {
    addAndLinkScene(defaultSceneType, MAIN_ID);
    switchToScene(MAIN_ID);
    logger.trace("set default scence");
  }

  private AbstractWindow getWindow() {
    return windowController.getWindow(windowID);
  }

  public WindowMediator getWindowController() {
    return windowController;
  }
  public FilesController getFilesController() {
    return filesController;
  }
  public void compile(){
    filesController.saveToFile();
    logger.debug("Compile");
  }

  @Override
  public void passData(Object data) {
    sceneData.push(data);
    logger.trace("passed data");
  }

  @Override
  public Object getData() {
    logger.trace("prepared data for a retrieve");
    return sceneData.pop();
  }
}
