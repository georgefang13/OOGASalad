package oogasalad.windowscene;

import oogasalad.windowscene.controllers.SceneTypes;
import oogasalad.windowscene.controllers.WindowMediator;
import oogasalad.windowscene.managers.PropertiesManager;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEnvironment {
    private static final String MAIN_ID = "main";

    protected WindowMediator windowController;
    protected Map<String, AbstractScene> scenes;

    //protected Manager manager = PropertiesFactory.createManager(); //TODO: pass in factory DI

    public AbstractEnvironment(WindowMediator windowController) {
        this.windowController = windowController;
        scenes = new HashMap<>();
        SceneTypes mainSceneType = getDefaultSceneType();
        addAndLinkScene(mainSceneType,MAIN_ID);
        switchToScene(MAIN_ID);
    }

    protected abstract SceneTypes getDefaultSceneType();
    protected abstract AbstractScene addNewScene(SceneTypes sceneType);

    public void addAndLinkScene(SceneTypes sceneType, String sceneID){
        AbstractScene newScene = addNewScene(sceneType);
        scenes.put(sceneID,newScene);
    }

    public void switchToScene(String sceneID) {
        //set scene to environment
        return;
    }

    public WindowMediator getWindowController() {
        return windowController;
    }
}
