package oogasalad.windowscene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameEditorMainScene extends AbstractScene {

  private Button editGridButton;

  private Label gameEditorLabel;

  public GameEditorMainScene(AbstractWindow window) {
    super(window);
  }

  @Override
  public Scene makeScene() {
    BorderPane root = new BorderPane();
    editGridButton = new Button();
    gameEditorLabel = new Label();
    root.setTop(gameEditorLabel);
    root.setLeft(new VBox(editGridButton));
    setScene(new Scene(root));
    setText();
    return getScene();
  }

  @Override
  public void setText() {
    editGridButton.setText(PropertiesManager.getText("GameEditorMainScene.EditGridButton"));
    gameEditorLabel.setText(PropertiesManager.getText("GameEditorMainScene.GameEditorLabel"));
  }
}
