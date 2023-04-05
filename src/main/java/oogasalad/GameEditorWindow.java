package oogasalad;

import java.util.HashMap;
import java.util.Map;
import oogasalad.SplashWindow.WindowScenes;

public class GameEditorWindow extends AbstractWindow {

  public enum WindowScenes implements SceneType {
    MAIN_SCENE
  }

  public GameEditorWindow(WindowMediator windowController) {
    super(windowController);
    switchToScene(WindowScenes.MAIN_SCENE);
  }

  @Override
  public Map<SceneType, AbstractScene> defineScenes() {
    Map<SceneType, AbstractScene> scenes = new HashMap<>();
    scenes.put(WindowScenes.MAIN_SCENE, new GameEditorMainScene(this));
    return scenes;
  }


}
