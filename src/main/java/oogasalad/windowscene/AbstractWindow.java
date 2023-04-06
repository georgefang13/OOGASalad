package oogasalad.windowscene;

import java.util.Map;
import javafx.stage.Stage;

public abstract class AbstractWindow extends Stage {

  protected WindowMediator windowController;
  protected Map<SceneTypes, AbstractScene> scenes;

  public AbstractWindow(WindowMediator windowController) {
    this.windowController = windowController;
    this.scenes = defineScenes();
    setWidth(PropertiesManager.getNumeric("WindowHeight"));
    setHeight(PropertiesManager.getNumeric("WindowWidth"));
  }

  public abstract Map<SceneTypes, AbstractScene> defineScenes();

  public void switchToScene(SceneTypes sceneTypes) {
    setScene(scenes.get(sceneTypes).makeScene());
  }
}
