package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.frontend.scenes.SplashMainScene;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class SplashWindow extends AbstractWindow {

  private static final MainLogger logger = MainLogger.getInstance(SplashWindow.class);

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE
  }

  public SplashWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
    logger.trace(String.format("Created new SplashWindow instance: ID - %s", windowID));
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      logger.trace("Added new SplashMainScene ↑");
      return new SplashMainScene(this.sceneController);
    }
    logger.warn("Invalid scene type: "); // TODO add a sceneType string
    throw new IllegalArgumentException("Invalid scene type: " + sceneType.toString());
  }

}
