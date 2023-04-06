package oogasalad.windowscene;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SplashMainScene extends AbstractScene {

  public SplashMainScene(AbstractWindow window) {
    super(window);
  }

  @Override
  public Scene makeScene() {
    StackPane root = new StackPane();
    VBox vbox = new VBox(10);
    Button playButton = new Button(Properties.getText("PlayGameButton"));
    playButton.setOnAction(e -> getWindow().windowController.registerAndShow(WindowTypes.WindowType.GAME_WINDOW));
    Button editButton = new Button(Properties.getText("EditGameButton"));
    editButton.setOnAction(e -> getWindow().windowController.registerAndShow(WindowTypes.WindowType.EDIT_WINDOW));
    vbox.getChildren().addAll(playButton, editButton);
    vbox.setAlignment(Pos.CENTER);
    root.getChildren().add(vbox);
    setScene(new Scene(root));
    return getScene();
  }

}
