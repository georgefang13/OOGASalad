package oogasalad.frontend.windows;

import javafx.stage.Stage;
import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.frontend.managers.PropertiesManager;
import oogasalad.frontend.scenes.AbstractScene;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public abstract class AbstractWindow extends Stage {
  private SceneController sceneController;

  public AbstractWindow(WindowMediator windowController) {
    sceneController = new SceneController(this,windowController); //maybe add just window controller and then the window id to get window from the window controller
    setWidth(PropertiesManager.getNumeric("WindowHeight"));
    setHeight(PropertiesManager.getNumeric("WindowWidth"));
  }

  public abstract SceneTypes getDefaultSceneType();
  public abstract AbstractScene addNewScene(SceneTypes sceneType);

  public void showScene(AbstractScene scene){
    setScene(scene.makeScene());
  }

  public WindowMediator getWindowController() {
    return windowController;
  }
}
