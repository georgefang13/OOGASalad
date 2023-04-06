package oogasalad.windowscene;

import java.util.Map;
import javafx.stage.Stage;

public abstract class AbstractWindow extends Stage {

  protected WindowMediator windowController;
  protected Map<SceneTypes, AbstractScene> scenes;

  public AbstractWindow(WindowMediator windowController) {
    this.windowController = windowController;
    scenes = defineScenes();
    setWidth(500); //TODO: Properties File
    setHeight(500); //TODO: Properties File
  }

  public abstract Map<SceneTypes, AbstractScene> defineScenes();

  public void switchToScene(SceneTypes sceneTypes) {
    setScene(scenes.get(sceneTypes).makeScene());
  }
}
