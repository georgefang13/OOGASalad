package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.GamePlayerLibraryScene;
import oogasalad.frontend.scenes.GamePlayerMainScene;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class LibraryWindow extends AbstractWindow {

  private static final MainLogger logger = MainLogger.getInstance(LibraryWindow.class);
  public enum WindowScenes implements SceneTypes {
    LIBRARY_SCENE,
    PLAY_SCENE
  }

  public LibraryWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
    logger.trace(String.format("Created a new instance of GamePlayerWindow: ID - %s", windowID));
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.LIBRARY_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.PLAY_SCENE)) {
      System.out.println("making new play scene");
      logger.trace("Added a new GamePlayerMainScene ↑");
      return new GamePlayerMainScene(this.sceneController); //TODO:FIX
    } else if (sceneType.equals(WindowScenes.LIBRARY_SCENE)) {
      logger.trace("Added a new GamePlayerLibraryScene ↑");
      return new GamePlayerLibraryScene(this.sceneController);
    }
    logger.warn("Invalid scene type: "); // TODO add a sceneType string
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);
  }


}
