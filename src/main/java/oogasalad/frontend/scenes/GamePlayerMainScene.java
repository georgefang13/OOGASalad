package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.managers.PropertiesManager;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {

  private Button playGameButton;

  private Label gamePlayerLabel;

  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    BorderPane root = new BorderPane();
    playGameButton = new Button();
    gamePlayerLabel = new Label();
    root.setTop(gamePlayerLabel);
    root.setLeft(new VBox(playGameButton));
    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }

  @Override
  public void setText() {
    playGameButton.setText(PropertiesManager.getText("GamePlayerMainScene.PlayGameButton"));
    gamePlayerLabel.setText(PropertiesManager.getText("GamePlayerMainScene.GamePlayerLabel"));
  }
}
