package oogasalad.windowscene;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

public abstract class AbstractWindow extends Stage {
  private static final String MAIN_ID = "main";

  protected WindowMediator windowController;
  protected Map<String, AbstractScene> scenes;

  public AbstractWindow(WindowMediator windowController) {
    this.windowController = windowController;
    scenes = new HashMap<>();
    SceneTypes mainSceneType = getDefaultSceneType();
    addAndLinkScene(mainSceneType,MAIN_ID);

    setWidth(500); //TODO: Properties File
    setHeight(500); //TODO: Properties File
    switchToScene(MAIN_ID);
  }

  protected abstract SceneTypes getDefaultSceneType();
  protected abstract AbstractScene addNewScene(SceneTypes sceneType);

  protected void addAndLinkScene(SceneTypes sceneType,String sceneID){
    AbstractScene newScene = addNewScene(sceneType);
    scenes.put(sceneID,newScene);
  }

  public void switchToScene(String sceneID) {
    setScene(scenes.get(sceneID).makeScene());
  }
}
