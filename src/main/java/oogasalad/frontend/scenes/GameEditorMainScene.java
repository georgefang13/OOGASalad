package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.windows.GameEditorWindow;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GameEditorMainScene extends AbstractScene {

  private Button editGridButton;

  private Label gameEditorLabel;

  public GameEditorMainScene(SceneMediator sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    BorderPane root = new BorderPane();

    editGridButton = new Button();
    editGridButton.setOnAction(
        e -> panelController.newSceneFromPanel("test", GameEditorWindow.WindowScenes.EDITOR_SCENE));

    gameEditorLabel = new Label();
    root.setTop(gameEditorLabel);
    root.setLeft(new VBox(editGridButton));
    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }

  @Override
  public void setText() {
    editGridButton.setText(getPropertyManager().getText("GameEditorMainScene.EditGridButton"));
    gameEditorLabel.setText(getPropertyManager().getText("GameEditorMainScene.GameEditorLabel"));
  }

}
