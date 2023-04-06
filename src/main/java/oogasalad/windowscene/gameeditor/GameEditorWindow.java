package oogasalad.windowscene.gameeditor;

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

public class GameEditorWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE,
    EDITOR_SCENE
  }

  public GameEditorWindow(WindowMediator windowController) {
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
    } else if (sceneType.equals(WindowScenes.EDITOR_SCENE)) {
      return new GameEditorEditorScene(this);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);

    //TODO: replace with reflection
  }


}
