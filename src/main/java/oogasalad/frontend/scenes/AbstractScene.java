package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import oogasalad.frontend.managers.PropertiesObserver;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.managers.ThemeManagerOld;
import oogasalad.frontend.managers.ThemeObserver;
import oogasalad.frontend.windows.AbstractWindow;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public abstract class AbstractScene implements PropertiesObserver, ThemeObserver {

  protected AbstractWindow window;

  protected Scene scene;
  protected PropertyManager propertyManager = StandardPropertyManager.getInstance();

  public AbstractScene(AbstractWindow window) {
    this.window = window;
    this.scene = makeScene();
    propertyManager.addObserver(this);
    ThemeManagerOld.addObserver(this);
  }

  public abstract Scene makeScene();

  protected Scene getScene() {
    return scene;
  }

  protected AbstractWindow getWindow() {
    return window;
  }

  protected PropertyManager getPropertyManager() {
    return propertyManager;
  }

  protected void setScene(Scene scene) {
    this.scene = scene;
  }

  public final void setTheme() {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(ThemeManagerOld.getTheme());
  }

}
