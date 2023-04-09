package oogasalad.frontend.panels;

import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.frontend.windows.GameEditorWindow;

import java.util.HashMap;
import java.util.Map;

public class PanelController {
    private Map<String,Panel> panelMap;
    private SceneController sceneController;


    public PanelController(SceneController sceneController) {
        this.sceneController = sceneController;
        panelMap = new HashMap<>();
    }

    public void newSceneFromPanel(String newSceneID, SceneTypes sceneType){
        sceneController.addAndLinkScene(sceneType,newSceneID);
        switchSceneFromPanel(newSceneID);
    }
    public void switchSceneFromPanel(String newSceneID){
        sceneController.switchToScene(newSceneID);
    }

    public SceneController getSceneController() {
        return sceneController;
    }
}
