package oogasalad;

import javafx.stage.Stage;

public abstract class AbstractWindow extends Stage {

  protected WindowMediator windowController;
  protected class PossibleSceneTypes;
  protected PossibleSceneTypes DefaultScene;

  public AbstractWindow(WindowMediator windowController) {
    this.windowController = windowController;
    setWidth(500); //TODO: Properties File
    setHeight(500); //TODO: Properties File
  }
  public abstract defineScenes();

}
