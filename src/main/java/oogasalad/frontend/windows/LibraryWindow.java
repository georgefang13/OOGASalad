package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.GamePlayerLibraryScene;
import oogasalad.frontend.scenes.GamePlayerMainScene;
import oogasalad.frontend.scenes.SceneTypes;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class LibraryWindow extends AbstractWindow {

  public enum WindowScenes implements SceneTypes {
    LIBRARY_SCENE,
    PLAY_SCENE
  }

  public LibraryWindow(String windowID, WindowMediator windowController) {
    super(windowID, windowController);
  }

  @Override
  public SceneTypes getDefaultSceneType() {
    return WindowScenes.LIBRARY_SCENE;
  } //TODO: OWEN FIX THIS back to library

  @Override
  public AbstractScene addNewScene(SceneTypes sceneType) {
    if (sceneType.equals(WindowScenes.PLAY_SCENE)) {
      System.out.println("making new play scene");
      return new GamePlayerMainScene(this.sceneController); //TODO:FIX
    } else if (sceneType.equals(WindowScenes.LIBRARY_SCENE)) {
      return new GamePlayerLibraryScene(this.sceneController);
    }
    throw new IllegalArgumentException("Invalid scene type: " + sceneType);
  }


}
