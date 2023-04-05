package oogasalad;

import java.util.HashMap;
import java.util.Map;

public class BaseSceneController implements SceneMediator{
    private Map<String, AbstractWindow> sceneMap;
    private int sceneIDCounter;

    public BaseSceneController() {
        sceneMap = new HashMap<>();
        showWindow(registerWindow(WindowTypeEnum.WindowType.SPLASH_WINDOW));

    }

    @Override
    public String registerScene() {
        AbstractScene scene = WindowFactory.createWindow(windowType, this);
        windowIDCounter++;
        String windowID = windowType + "_" + windowIDCounter;
        return null;
    }

    @Override
    public void switchScene(String windowID) {

    }

    @Override
    public void receiveMessage() {

    }
}
