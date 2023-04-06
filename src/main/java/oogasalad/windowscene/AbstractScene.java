package oogasalad.windowscene;


import javafx.scene.Scene;

public abstract class AbstractScene {

  protected AbstractWindow window;
  protected Scene scene;

  public AbstractScene(AbstractWindow window) {
    this.window = window;
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
}
