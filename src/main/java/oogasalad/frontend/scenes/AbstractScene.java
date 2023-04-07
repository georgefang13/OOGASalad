package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import oogasalad.frontend.managers.PropertiesManager;
import oogasalad.frontend.managers.PropertiesObserver;
import oogasalad.frontend.managers.ThemeManager;
import oogasalad.frontend.managers.ThemeObserver;
import oogasalad.frontend.windows.AbstractWindow;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public abstract class AbstractScene implements PropertiesObserver, ThemeObserver {

  protected SceneMediator sceneController;

  protected Scene scene;

  public AbstractScene(SceneMediator sceneController) {
    this.sceneController = sceneController;
    this.scene = makeScene();
    PropertiesManager.addObserver(this);
    ThemeManager.addObserver(this);
  }

  public abstract Scene makeScene();

  protected Scene getScene() {
    return scene;
  }

  protected AbstractWindow getWindow() {
    return window;
  }

  protected void setScene(Scene scene) {
    this.scene = scene;
  }

  public final void setTheme() {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(ThemeManager.getTheme());
  }

}
