package oogasalad.windowscene.gameplayer;

import java.util.HashMap;
import java.util.Map;
import oogasalad.windowscene.AbstractScene;
import oogasalad.windowscene.AbstractWindow;
import oogasalad.windowscene.controllers.SceneTypes;
import oogasalad.windowscene.controllers.WindowMediator;

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
