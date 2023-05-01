package oogasalad.frontend.scenes;

import oogasalad.Controller.FilesController;
import oogasalad.frontend.windows.WindowMediator;

public interface SceneMediator {
    void switchToScene(String sceneID);
    void addAndLinkScene(SceneTypes sceneType, String sceneID);
    void setDefaultScene(SceneTypes defaultSceneType);
    WindowMediator getWindowController();
    void passData(Object data);
    Object getData();
    FilesController getFilesController();
    void compile();


}
