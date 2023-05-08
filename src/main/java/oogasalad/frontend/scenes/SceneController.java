package oogasalad.frontend.scenes;

import oogasalad.Controller.FilesController;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.WindowMediator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
/**
 * @author George Fang
 * @author Owen MacKenzie
 * @author Connor Wells
 *
 * The purpose of this class is to manage different scenes in the GUI, allowing the user to switch
 * between them. It keeps track of the current scene and provides methods to add new scenes, set the
 * default scene, and switch to a different scene. The SceneController constructor takes a windowID
 * and a WindowMediator as parameters. The windowID parameter is used to identify which window the
 * controller belongs to, and the WindowMediator parameter is used to control the window. The
 * SceneController class contains a map of AbstractScene objects, which represent different scenes
 * in the GUI. The scenes are identified by a unique string ID. The switchToScene method sets the
 * current scene to the one with the given ID and shows it on the window. The addAndLinkScene method
 * creates a new scene of the given type and adds it to the map with the given ID, if it doesn't
 * already exist. The setDefaultScene method sets the default scene to the one with the given type
 * and shows it on the window. The passData and getData methods are used to push and pop data to/from
 * a stack. The compile method is used to save the files. The getGameName method gets the name of the
 * game from the current window. The getWindowController and getFilesController methods return the
 * WindowMediator and FilesController objects, respectively. Finally, the setFilesController
 * method is used to set the FilesController object.
 */
public class SceneController implements SceneMediator{

  private static final String MAIN_ID = "main";
  private AbstractScene currentScene;
  private Map<String, AbstractScene> scenes;
  private String windowID;
  private WindowMediator windowController;
  private FilesController filesController;
  private Stack<Object> sceneData = new Stack<>();

  /**
   * Constructor for the scene controller
   * @param windowID
   * @param windowController
   */
  public SceneController(String windowID, WindowMediator windowController) {
    this.windowID = windowID;
    this.windowController = windowController;
    scenes = new HashMap<>();
    filesController = new FilesController();
  }

  /**
   * Switches to the scene with the given ID
   * @param sceneID
   */
  public void switchToScene(String sceneID) {
    currentScene = scenes.get(sceneID);
    getWindow().showScene(currentScene);
  }

  /**
   * Adds and links a scene with the given ID and type
   * @param sceneType
   * @param sceneID
   */
  public void addAndLinkScene(SceneTypes sceneType, String sceneID) {
    if (!scenes.containsKey(sceneID)) {
      AbstractScene newScene = getWindow().addNewScene(sceneType);
      scenes.put(sceneID, newScene);
    }
  }

  /**
   * Sets the default scene to the scene with the given type
   * @param defaultSceneType
   */
  public void setDefaultScene(SceneTypes defaultSceneType) {
    addAndLinkScene(defaultSceneType, MAIN_ID);
    switchToScene(MAIN_ID);
  }

  /**
   * Gets the current window
   * @return
   */
  @Override
  public AbstractWindow getWindow() {
    return windowController.getWindow(windowID);
  }

  /**
   * Gets the window controller
   * @return
   */
  public WindowMediator getWindowController() {
    return windowController;
  }

  /**
   * Gets the files controller
   * @return
   */
  public FilesController getFilesController() {
    return filesController;
  }

  /**
   * Compiles files
   */
  public void compile(){
    filesController.saveToFile();
  }

  /**
   * Passes/pushes data to a stack
   * @param data
   */
  @Override
  public void passData(Object data) {
    sceneData.push(data);
  }

  /**
   * Gets/pops data from a stack
   * @return
   */
  @Override
  public Object getData() {
    return sceneData.pop();
  }

  /**
   * Gets the game name
   * @return
   */
  @Override
  public String getGameName() {
    System.out.println("inside scene controller");
    String gamenaem = getWindow().getGameName();
    System.out.println(gamenaem);
    return gamenaem;
  }

  /**
   * Sets the files controller
   * @param files
   */
  public void setFilesController(FilesController files){
    filesController = files;
  }
}
