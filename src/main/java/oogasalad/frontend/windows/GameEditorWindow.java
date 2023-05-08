package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.GameEditorEditorScene;
import oogasalad.frontend.scenes.GameEditorLogicScene;
import oogasalad.frontend.scenes.GameEditorMainScene;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.logging.MainLogger;
import ch.qos.logback.classic.Level;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GameEditorWindow extends AbstractWindow {
  private static final MainLogger logger = MainLogger.getInstance(GameEditorWindow.class);

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE,
    EDITOR_SCENE,
    LOGIC_SCENE,
  }

  public GameEditorWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
    logger.trace(String.format("Created a new GameEditorWindow Instance: ID - %s", windowID));
    gameName = windowController.getData().toString();
    System.out.println("inside game editor window constructor");
    System.out.println(gameName);
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      logger.trace("Added new GameEditorMainScene ↑");
      return new GameEditorMainScene(this.sceneController);
    } else if (sceneType.equals(WindowScenes.EDITOR_SCENE)) {
      logger.trace("Added new GameEditorEditorScene ↑");
      return new GameEditorEditorScene(this.sceneController);
    } else if (sceneType.equals(WindowScenes.LOGIC_SCENE)) {
      logger.trace("Added new GameEditorLogicScene ↑");
      System.out.println("I made with this: " + this.sceneController);
      return new GameEditorLogicScene(this.sceneController);
    }
    logger.warn("Invalid scene type: "); // TODO add a sceneType string
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);

    //TODO: replace with reflection
  }


}
