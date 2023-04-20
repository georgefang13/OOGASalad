package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.windows.GameEditorWindow;
import oogasalad.frontend.windows.GamePlayerWindow;

public class GamePlayerLibraryScene extends AbstractScene {
  private Button editGridButton;

  private Label gameEditorLabel;

  public GamePlayerLibraryScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    BorderPane root = new BorderPane();

    editGridButton = new Button("IN THE LIBRARY: CLICK TO Edit Grid");
    editGridButton.setOnAction(
        e -> panelController.newSceneFromPanel("test", GamePlayerWindow.WindowScenes.PLAY_SCENE));

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

  }
}
