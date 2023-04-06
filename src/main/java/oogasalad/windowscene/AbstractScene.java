package oogasalad.windowscene;


import java.util.ResourceBundle;
import javafx.scene.Scene;

public abstract class AbstractScene implements LanguageObserver {

  protected AbstractWindow window;

  protected Scene scene;
  protected ResourceBundle textResources;
  protected ResourceBundle numericResources;
  protected String stylesheet;

  public AbstractScene(AbstractWindow window) {
    this.window = window;
    setTheme("dark");
    Properties.addObserver(this);
  }

  public abstract Scene makeScene();

  public void setTheme(String theme) {
    stylesheet = theme + ".css";
  }

  protected Scene getScene() {
    return scene;
  }

  protected AbstractWindow getWindow() {
    return window;
  }

  protected void setScene(Scene scene) {
    this.scene = scene;
  }

  protected void updateStylesheet() {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(stylesheet);
  }

}
