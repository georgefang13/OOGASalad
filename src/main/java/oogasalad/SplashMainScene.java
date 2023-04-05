package oogasalad;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SplashMainScene extends AbstractScene {

  private Scene scene;

  @Override
  public Scene makeScene() {
    StackPane root = new StackPane();
    VBox vbox = new VBox(10);
    Button playButton = new Button("Play Game"); //TODO: Properties File
    playButton.setOnAction(e -> {});
    Button editButton = new Button("Edit Game"); //TODO: Properties File
    editButton.setOnAction(e -> {  });
    vbox.getChildren().addAll(playButton, editButton);
    vbox.setAlignment(Pos.CENTER);
    root.getChildren().add(vbox);
    scene = new Scene(root);
    return getScene();
  }

  private Scene getScene() {
    return scene;
  }
}
