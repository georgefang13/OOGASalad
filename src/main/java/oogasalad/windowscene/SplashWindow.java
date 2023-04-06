package oogasalad.windowscene;

import java.util.HashMap;
import java.util.Map;

public class SplashWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    MAIN_SCENE
  }

  public SplashWindow(WindowMediator windowController) {
    super(windowController);
  }

  @Override
  protected SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  protected AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      return new SplashMainScene(this);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType.toString());
  }

}
