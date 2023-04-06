package oogasalad.windowscene;


import java.util.ResourceBundle;
import javafx.scene.Scene;

public abstract class AbstractScene {

  protected AbstractWindow window;

  protected Scene scene;
  protected ResourceBundle textResources;
  protected ResourceBundle numericResources;
  protected String stylesheet;

  public AbstractScene(AbstractWindow window) {
    this.window = window;
    setTextResources("english.properties");
    setNumericResources("numeric.properties");
    setTheme("default.css");
  }

  public abstract Scene makeScene();

  public void setTheme(String theme) {
    stylesheet = theme + ".css";
    updateStylesheet();
  }

  public void setTextResources(String resource) {
    textResources = ResourceBundle.getBundle(resource);
    updateTextResources();
  }

  public void setNumericResources(String resource) {
    numericResources = ResourceBundle.getBundle(resource);
    updateNumericResources();
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
  
  protected abstract void updateTextResources();

  protected abstract void updateNumericResources();

  protected void updateStylesheet() {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(stylesheet);
  }

}
