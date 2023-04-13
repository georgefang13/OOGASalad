package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.*;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE,
    PLAY_SCENE
  }

  public GamePlayerWindow(String windowID, WindowMediator windowController) {
    super(windowID,windowController);
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      return new GamePlayerMainScene(this.sceneController);
    } else if (sceneType.equals(WindowScenes.PLAY_SCENE)) {
      return new GameEditorEditorScene(this.sceneController);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);
  }


}
