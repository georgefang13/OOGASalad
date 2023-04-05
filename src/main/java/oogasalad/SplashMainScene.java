package oogasalad;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SplashMainScene extends AbstractScene {

  private Scene scene;

  @Override
  public Scene makeScene() {
    scene = new Scene(new VBox(new Button("Play Game"), new Button("Edit Game")));
    return getScene();
  }

  private Scene getScene() {
    return scene;
  }
}
