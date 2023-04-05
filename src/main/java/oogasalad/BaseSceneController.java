package oogasalad;

import javafx.scene.Scene;

import java.util.HashMap;
import java.util.Map;

public class BaseSceneController implements SceneMediator{
    protected Map<String, AbstractScene> sceneMap;
    protected int sceneIDCounter;
    protected AbstractWindow abstractWindow;

    public BaseSceneController(AbstractWindow abstractWindow) {
        this.abstractWindow = abstractWindow;
        sceneMap = new HashMap<>();
        switchScene(registerScene());

    }

    @Override
    public String registerScene() {
        AbstractScene scene = new SplashMainScene(); //TODO: make it so you can use enum specific to the window type
        String sceneID = "scene_" + sceneIDCounter;
        sceneMap.put(sceneID, scene);
        return sceneID;
    }
    @Override
    public void switchScene(String sceneID) {
        AbstractScene scene = sceneMap.get(sceneID);
        sceneWindow.setScene(scene.makeScene());
    }

    @Override
    public void receiveMessage() {

    }
}
