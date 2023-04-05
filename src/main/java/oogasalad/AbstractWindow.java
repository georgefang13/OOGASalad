package oogasalad;

import java.util.Map;
import javafx.stage.Stage;

public abstract class AbstractWindow extends Stage {

  protected WindowMediator windowController;
  protected Map<SceneType, AbstractScene> scenes;

  public AbstractWindow(WindowMediator windowController) {
    this.windowController = windowController;
    scenes = defineScenes();
    setWidth(500); //TODO: Properties File
    setHeight(500); //TODO: Properties File
  }

  public abstract Map<SceneType, AbstractScene> defineScenes();

  public void switchToScene(SceneType sceneType) {
    setScene(scenes.get(sceneType).makeScene());
  }
}
