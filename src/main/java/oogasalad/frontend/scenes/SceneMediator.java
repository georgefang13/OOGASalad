package oogasalad.frontend.scenes;

import oogasalad.Controller.FilesController;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.WindowMediator;

/**
 * @author Owen MacKenzie
 * @author Connor Wells
 */

public interface SceneMediator {
    void switchToScene(String sceneID);
    void addAndLinkScene(SceneTypes sceneType, String sceneID);
    void setDefaultScene(SceneTypes defaultSceneType);
    WindowMediator getWindowController();
    AbstractWindow getWindow();
    void passData(Object data);
    Object getData();
    FilesController getFilesController();
    void compile();
    String getGameName();
    void setFilesController(FilesController files);
}
