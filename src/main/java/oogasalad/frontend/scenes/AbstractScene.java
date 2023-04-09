package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import oogasalad.frontend.managers.PropertiesManager;
import oogasalad.frontend.managers.PropertiesObserver;
import oogasalad.frontend.managers.ThemeManager;
import oogasalad.frontend.managers.ThemeObserver;
import oogasalad.frontend.panels.PanelController;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public abstract class AbstractScene implements PropertiesObserver, ThemeObserver {

  protected PanelController panelController;

  protected Scene scene;

  public AbstractScene(SceneController sceneController) {
    this.panelController = new PanelController(sceneController);
    this.scene = makeScene();
    PropertiesManager.addObserver(this);
    ThemeManager.addObserver(this);
  }

  public abstract Scene makeScene();

  protected Scene getScene() {
    return scene;
  }

  protected void setScene(Scene scene) {
    this.scene = scene;
  }

  public final void setTheme() {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(ThemeManager.getTheme());
  }
}
