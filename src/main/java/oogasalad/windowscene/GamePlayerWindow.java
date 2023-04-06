package oogasalad.windowscene;

import java.util.HashMap;
import java.util.Map;

public class GamePlayerWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE,
    PLAY_SCENE
  }

  public GamePlayerWindow(WindowMediator windowController) {
    super(windowController);
  }

  @Override
  protected SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  protected AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      return new GameEditorMainScene(this);
    } else if (sceneType.equals(WindowScenes.PLAY_SCENE)) {
      return new GameEditorEditorScene(this);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);
  }


}
