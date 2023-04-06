package oogasalad.windowscene.modal;

import oogasalad.windowscene.AbstractScene;
import oogasalad.windowscene.AbstractWindow;
import oogasalad.windowscene.controllers.SceneTypes;
import oogasalad.windowscene.controllers.WindowMediator;
import oogasalad.windowscene.modal.ModalScene;

public class ModalWindow extends AbstractWindow {
    public ModalWindow(WindowMediator windowController) {
        super(windowController);
    }

    public enum WindowScenes implements SceneTypes {
        MAIN_SCENE
    }
    @Override
    protected SceneTypes getDefaultSceneType() {
        return WindowScenes.MAIN_SCENE;
    }

    @Override
    protected AbstractScene addNewScene(SceneTypes sceneType) {
        if (sceneType.equals(WindowScenes.MAIN_SCENE)) {
            return new ModalScene(this);
        }
        throw new IllegalArgumentException("Invalid scene type: " + sceneType);
    }
}


