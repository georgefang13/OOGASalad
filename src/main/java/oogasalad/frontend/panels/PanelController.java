package oogasalad.frontend.panels;

import oogasalad.frontend.scenes.SceneMediator;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.WindowTypes.WindowType;

import java.util.HashMap;
import java.util.Map;
/**
 * @author George Fang
 * @author Owen MacKenzie
 */
public class PanelController {

  private Map<String, Panel> panelMap;
  private SceneMediator sceneController;


  public PanelController(SceneMediator sceneController) {
    this.sceneController = sceneController;
    panelMap = new HashMap<>();
  }
  public void updatePanel(String panelID){
    panelMap.get(panelID).makePanel();
  }
  public void registerPanel(String panelID,Panel panel){
    panelMap.put(panelID,panel);
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

  public void compile(){
    sceneController.compile();
  }
  public void switchSceneFromPanel(String newSceneID) {
    sceneController.switchToScene(newSceneID);
  }

  public SceneMediator getSceneController() {
    return sceneController;
  }

  public void addPanel(String panelID, Panel panel) {
    panelMap.put(panelID, panel);
  }
}
