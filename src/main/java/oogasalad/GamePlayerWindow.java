package oogasalad;

import java.util.HashMap;
import java.util.Map;

public class GamePlayerWindow extends AbstractWindow {

  public enum WindowScenes implements SceneType {
    MAIN_SCENE
  }

  public GamePlayerWindow(WindowMediator windowController) {
    super(windowController);
    switchToScene(SplashWindow.WindowScenes.MAIN_SCENE);

  }

  @Override
  public Map<SceneType, AbstractScene> defineScenes() {
    Map<SceneType, AbstractScene> scenes = new HashMap<>();
    scenes.put(WindowScenes.MAIN_SCENE, new SplashMainScene(this));
    return scenes;
  }

}
