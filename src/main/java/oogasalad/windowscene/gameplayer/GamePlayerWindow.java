package oogasalad.windowscene.gameplayer;

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
