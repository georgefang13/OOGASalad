package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.windows.AbstractWindow;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {

  private Button playGameButton;

  private Label gamePlayerLabel;

  public GamePlayerMainScene(AbstractWindow window) {
    super(window);
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
    playGameButton.setText(getPropertyManager().getText("GamePlayerMainScene.PlayGameButton"));
    gamePlayerLabel.setText(getPropertyManager().getText("GamePlayerMainScene.GamePlayerLabel"));
  }
}
