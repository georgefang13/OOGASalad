package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.GameEditorEditorScene;
import oogasalad.frontend.scenes.GameEditorLogicScene;
import oogasalad.frontend.scenes.GameEditorMainScene;
import oogasalad.frontend.scenes.SceneTypes;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GameEditorWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE,
    EDITOR_SCENE,
    LOGIC_SCENE,
  }

  public GameEditorWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      return new GameEditorMainScene(this.sceneController);
    } else if (sceneType.equals(WindowScenes.EDITOR_SCENE)) {
      return new GameEditorEditorScene(this.sceneController);
    } else if (sceneType.equals(WindowScenes.LOGIC_SCENE)) {
      return new GameEditorLogicScene(this.sceneController);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);

    //TODO: replace with reflection
  }


}
