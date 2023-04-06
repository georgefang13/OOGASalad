package oogasalad.windowscene;

import java.util.HashMap;
import java.util.Map;

public class GamePlayerWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE
  }

  public GamePlayerWindow(WindowMediator windowController) {
    super(windowController);
    switchToScene(WindowScenes.MAIN_SCENE);

  }

  @Override
  public Map<SceneTypes, AbstractScene> defineScenes() {
    Map<SceneTypes, AbstractScene> scenes = new HashMap<>();
    scenes.put(WindowScenes.MAIN_SCENE, new GamePlayerMainScene(this));
    return scenes;
  }

}
