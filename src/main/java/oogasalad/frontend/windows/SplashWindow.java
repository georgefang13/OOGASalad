package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SplashMainScene;
import oogasalad.frontend.scenes.SceneTypes;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

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
