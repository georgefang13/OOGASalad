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
    ComboBox<String> languageDropdown = new ComboBox<>();
    languageDropdown.getItems().addAll("english", "spanish");
    languageDropdown.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          PropertiesManager.setTextResources(newValue);
        });
    languageDropdown.setValue("english");
    ComboBox<String> themeDropdown = new ComboBox<>();
    themeDropdown.getItems().addAll("light", "dark");
    themeDropdown.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {

        });
    themeDropdown.setValue("light");
    vbox.getChildren().addAll(playButton, editButton, languageDropdown, themeDropdown);
    vbox.setAlignment(Pos.CENTER);
    root.getChildren().add(vbox);
    setScene(new Scene(root));
    setText();
    return getScene();
  }

  @Override
  public void setText() {
    playButton.setText(PropertiesManager.getText("SplashMainScene.PlayGameButton"));
    editButton.setText(PropertiesManager.getText("SplashMainScene.EditGameButton"));
  }
}
