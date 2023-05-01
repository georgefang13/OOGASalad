package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.ModalScene;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

public class ModalWindow extends AbstractWindow {
  private static final MainLogger logger = MainLogger.getInstance(ModalWindow.class);

  public ModalWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
    logger.trace(String.format("Created a new ModalWindow instance: ID - %s", windowID));
  }

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      logger.trace("Added new ModalScene ↑");
      return new ModalScene(this.sceneController);
    }
    logger.warn("Invalid scene type: "); // TODO add a sceneType string
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);
  }
}


