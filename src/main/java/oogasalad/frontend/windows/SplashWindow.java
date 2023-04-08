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

  public SplashWindow(String windowID, WindowMediator windowController) {
    super(windowID,windowController);
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.MAIN_SCENE;
  }

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
      return new SplashMainScene(this.sceneController);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType.toString());
  }

}
