package oogasalad;

import java.util.HashMap;
import java.util.Map;

public class GameEditorWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE
  }

  public GameEditorWindow(WindowMediator windowController) {
    super(windowController);
    switchToScene(WindowScenes.MAIN_SCENE);
  }

  @Override
  public Map<SceneTypes, AbstractScene> defineScenes() {
    Map<SceneTypes, AbstractScene> scenes = new HashMap<>();
    scenes.put(WindowScenes.MAIN_SCENE, new GameEditorMainScene(this));
    return scenes;
  }


}
