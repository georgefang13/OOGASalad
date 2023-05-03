package oogasalad.frontend.scenes;

import oogasalad.Controller.FilesController;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.WindowMediator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
/**
 * @author Owen MacKenzie
 * @author Connor Wells
 */
public class SceneController implements SceneMediator{

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
    filesController = new FilesController();
  }

  public void switchToScene(String sceneID) {
    currentScene = scenes.get(sceneID);
    getWindow().showScene(currentScene); //TODO: USE OBSERVER PATTERN TO DO THIS
  }

  public void addAndLinkScene(SceneTypes sceneType, String sceneID) {
    if (!scenes.containsKey(sceneID)) {
      AbstractScene newScene = getWindow().addNewScene(sceneType);
      scenes.put(sceneID, newScene);
    }
  }

  public void setDefaultScene(SceneTypes defaultSceneType) {
    addAndLinkScene(defaultSceneType, MAIN_ID);
    switchToScene(MAIN_ID);
  }
  @Override
  public AbstractWindow getWindow() {
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
  }

  @Override
  public void passData(Object data) {
    sceneData.push(data);
  }

  @Override
  public Object getData() {
    return sceneData.pop();
  }
  @Override
  public String getGameName() {
    return getWindow().getGameName();
  }

  public void setFilesController(FilesController files){
    filesController = files;
  }
}
