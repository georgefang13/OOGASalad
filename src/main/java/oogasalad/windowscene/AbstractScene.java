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
    setTextResources("spanish");
    setNumericResources("numeric");
    setTheme("dark");
  }

  public abstract Scene makeScene();

  public void setTheme(String theme) {
    stylesheet = theme + ".css";
  }

  public void setTextResources(String resource) {
    textResources = ResourceBundle.getBundle("frontend/properties/text/" + resource);
  }

  public void setNumericResources(String resource) {
    numericResources = ResourceBundle.getBundle("frontend/properties/numeric/" + resource);
  }

  protected Scene getScene() {
    return scene;
  }

  protected AbstractWindow getWindow() {
    return window;
  }

  protected String getText(String key) {
    return textResources.getString(key);
  }

  protected String getNumeric(String key) {
    return numericResources.getString(key);
  }

  protected void setScene(Scene scene) {
    this.scene = scene;
  }

  protected void updateStylesheet() {
    scene.getStylesheets().clear();
    scene.getStylesheets().add(stylesheet);
  }

}
