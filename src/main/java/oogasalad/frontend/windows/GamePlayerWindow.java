package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.GameEditorEditorScene;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.frontend.scenes.GameEditorMainScene;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE,
    PLAY_SCENE
  }

  public GamePlayerWindow(WindowMediator windowController) {
    super(windowController);
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      return new GameEditorMainScene(this);
    } else if (sceneType.equals(WindowScenes.PLAY_SCENE)) {
      return new GameEditorEditorScene(this);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);
  }


}
