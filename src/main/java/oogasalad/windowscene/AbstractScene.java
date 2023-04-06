package oogasalad.windowscene;

import javafx.scene.Scene;

public abstract class AbstractScene implements PropertiesObserver, ThemeObserver {

  protected AbstractWindow window;

  protected Scene scene;

  public AbstractScene(AbstractWindow window) {
    this.window = window;
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
