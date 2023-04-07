package oogasalad.frontend.scenes;

import oogasalad.frontend.windows.AbstractWindow;

import java.util.HashMap;
import java.util.Map;

public class SceneController {
    private static final String MAIN_ID = "main";
    private AbstractScene currentScene;
    private Map<String, AbstractScene> scenes;
    private AbstractWindow window;

    public SceneController(AbstractWindow window) {
        this.window = window;
        scenes = new HashMap<>();
        addAndLinkScene(this.window.getDefaultSceneType(),MAIN_ID);
        switchToScene(MAIN_ID);
    }

    public void switchToScene(String sceneID) {
        currentScene = scenes.get(sceneID);
        window.showScene(currentScene); //TODO: USE OBSERVER PATTERN TO DO THIS
    }

    public void addAndLinkScene(SceneTypes sceneType, String sceneID){
        AbstractScene newScene = window.addNewScene(sceneType);
        scenes.put(sceneID,newScene);
    }


}
