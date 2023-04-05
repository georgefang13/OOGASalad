package oogasalad;

import java.util.Map;

public class GameEditorWindow extends AbstractWindow {

  public GameEditorWindow(WindowMediator windowController) {
    super(windowController);
    AbstractScene scene = new SplashMainScene();
    setScene(scene.makeScene());
  }

  @Override
  public Map<SceneType, AbstractScene> defineScenes() {
    return null;
  }


}
