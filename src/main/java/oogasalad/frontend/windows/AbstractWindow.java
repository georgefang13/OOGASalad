package oogasalad.frontend.windows;

import ch.qos.logback.classic.Level;
import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public abstract class AbstractWindow extends Stage {

  protected SceneController sceneController;
  protected String windowID;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();
  private static final MainLogger logger = MainLogger.getInstance(AbstractWindow.class);

  public AbstractWindow(String windowID, WindowMediator windowController) {
    this.windowID = windowID;
    sceneController = new SceneController(windowID,
        windowController); //maybe add just window controller and then the window id to get window from the window controller
    setWidth(propertyManager.getNumeric("WindowWidth"));
    setHeight(propertyManager.getNumeric("WindowHeight"));

    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
    logger.trace("Created a new scene ↑");
  }

  public abstract SceneTypes getDefaultSceneType();

  public abstract AbstractScene addNewScene(SceneTypes sceneType);

  public void showScene(AbstractScene scene) {
    logger.trace("Show a new scene ↑");
    setScene(scene.makeScene());
  }
}
