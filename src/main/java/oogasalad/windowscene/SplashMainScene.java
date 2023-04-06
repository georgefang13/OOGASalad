package oogasalad.windowscene;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SplashMainScene extends AbstractScene {

  private Button playButton, editButton;

  public SplashMainScene(AbstractWindow window) {
    super(window);
  }

  @Override
  public Scene makeScene() {
    StackPane root = new StackPane();
    VBox vbox = new VBox(10);
    playButton = new Button();
    playButton.setOnAction(
        e -> getWindow().windowController.registerAndShow(WindowTypes.WindowType.GAME_WINDOW));
    editButton = new Button();
    editButton.setOnAction(
        e -> getWindow().windowController.registerAndShow(WindowTypes.WindowType.EDIT_WINDOW));
    updateText();
    ComboBox<String> languageDropdown = new ComboBox<>();
    languageDropdown.getItems().addAll("english", "spanish");
    languageDropdown.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          Properties.setTextResources(newValue);
        });
    languageDropdown.setValue("english");
    vbox.getChildren().addAll(playButton, editButton, languageDropdown);
    vbox.setAlignment(Pos.CENTER);
    root.getChildren().add(vbox);
    setScene(new Scene(root));
    return getScene();
  }

  @Override
  public void updateText() {
    playButton.setText(Properties.getText("SplashMainScene.PlayGameButton"));
    editButton.setText(Properties.getText("SplashMainScene.EditGameButton"));
  }
}
