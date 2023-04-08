package oogasalad.frontend.scenes;

import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.WindowController;
import oogasalad.frontend.windows.WindowMediator;

import java.util.HashMap;
import java.util.Map;

public class SceneController {
    private static final String MAIN_ID = "main";
    private AbstractScene currentScene;
    private Map<String, AbstractScene> scenes;
    private String windowID;
    private WindowMediator windowController;

    public SceneController(String windowID,WindowMediator windowController) {
        this.windowID = windowID;
        this.windowController = windowController;
        scenes = new HashMap<>();
    }

    public void switchToScene(String sceneID) {
        currentScene = scenes.get(sceneID);
        getWindow().showScene(currentScene); //TODO: USE OBSERVER PATTERN TO DO THIS
    }

    public void addAndLinkScene(SceneTypes sceneType, String sceneID){
        AbstractScene newScene = getWindow().addNewScene(sceneType);
        scenes.put(sceneID,newScene);
    }
    public void setDefaultScene(SceneTypes defaultSceneType){
        addAndLinkScene(defaultSceneType,MAIN_ID);
        switchToScene(MAIN_ID);
    }

    private AbstractWindow getWindow(){
        return windowController.getWindow(windowID);
    }

    public WindowController getWindowController(){
        return (WindowController) windowController;
    }


}
