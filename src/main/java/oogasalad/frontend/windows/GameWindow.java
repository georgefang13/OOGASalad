package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.GamePlayerLibraryScene;
import oogasalad.frontend.scenes.GamePlayerMainScene;
import oogasalad.frontend.scenes.SceneTypes;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GameWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    PLAYER_SELECT_SCENE,
    GAME_SCENE,
    WIN_SCENE
  }

  public GameWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.GAME_SCENE;
  } //TODO: OWEN FIX THIS back to library

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.PLAYER_SELECT_SCENE)) {
      return new GamePlayerMainScene(this.sceneController); //TODO:FIX
    } else if (sceneType.equals(WindowScenes.WIN_SCENE)) {
      return new GamePlayerLibraryScene(this.sceneController); //TODO:FIX
    } else if (sceneType.equals(WindowScenes.GAME_SCENE)) {
      return new GamePlayerMainScene(this.sceneController); //TODO:FIX
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);
  }


}
