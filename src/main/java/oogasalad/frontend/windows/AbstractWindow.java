package oogasalad.frontend.windows;

import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.scenes.SceneTypes;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public abstract class AbstractWindow extends Stage {
  protected String gameName;

  protected SceneController sceneController;
  protected String windowID;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();

  public AbstractWindow(String windowID, WindowMediator windowController) {
    this.windowID = windowID;
    sceneController = new SceneController(windowID,
        windowController); //maybe add just window controller and then the window id to get window from the window controller
    setWidth(propertyManager.getNumeric("WindowWidth"));
    setHeight(propertyManager.getNumeric("WindowHeight"));
  }

  public abstract SceneTypes getDefaultSceneType();

  public abstract AbstractScene addNewScene(SceneTypes sceneType);

  public void showScene(AbstractScene scene) {
    setScene(scene.getScene());
  }
  public String getGameName(){
    return gameName;
  }
}
