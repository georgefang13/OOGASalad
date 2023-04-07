package oogasalad.frontend.windows;

import oogasalad.frontend.scenes.ModalScene;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SceneTypes;

public class ModalWindow extends AbstractWindow {
    public ModalWindow(WindowMediator windowController) {
        super(windowController);
    }

    public enum WindowScenes implements SceneTypes {
        MAIN_SCENE
    }
    @Override
    public SceneTypes getDefaultSceneType() {
        return WindowScenes.MAIN_SCENE;
    }

    @Override
    public AbstractScene addNewScene(SceneTypes sceneType) {
        if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
            return new ModalScene(this);
        }
        throw new IllegalArgumentException("Invalid scene type: " + sceneType);
    }
}


