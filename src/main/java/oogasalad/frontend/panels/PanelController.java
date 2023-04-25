package oogasalad.frontend.panels;

import java.util.HashMap;
import java.util.Map;
import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.WindowTypes.WindowType;

public class PanelController {

  private Map<String, Panel> panelMap;
  private SceneController sceneController;


  public PanelController(SceneController sceneController) {
    this.sceneController = sceneController;
    panelMap = new HashMap<>();
  }

  public void newSceneFromPanel(String newSceneID, SceneTypes sceneType) {
    sceneController.addAndLinkScene(sceneType, newSceneID);
    switchSceneFromPanel(newSceneID);
  }
  public AbstractWindow newWindowFromPanel(WindowType windowType) {
    String newWindowID = sceneController.getWindowController().registerWindow(windowType);
    sceneController.getWindowController().showWindow(newWindowID);
    return sceneController.getWindowController().getWindow(newWindowID);
  }

  public void switchSceneFromPanel(String newSceneID) {
    sceneController.switchToScene(newSceneID);
  }

  public SceneController getSceneController() {
    return sceneController;
  }

  public void addPanel(String panelID, Panel panel) {
    panelMap.put(panelID, panel);
  }
}
