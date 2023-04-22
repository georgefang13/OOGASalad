package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.libraryPanels.LibraryGridPanel;
import oogasalad.frontend.panels.libraryPanels.SortedGamesPanel;
import oogasalad.frontend.windows.GamePlayerWindow;

public class GamePlayerLibraryScene extends AbstractScene {
  private Button editGridButton;
  private Label gameEditorLabel;
  private BorderPane root;
  private SortedGamesPanel sortedGamesPanel;

  public GamePlayerLibraryScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();

    editGridButton = new Button("IN THE LIBRARY: CLICK TO Edit Grid");
    editGridButton.setOnAction(
        e -> panelController.newSceneFromPanel("test", GamePlayerWindow.WindowScenes.PLAY_SCENE));

    gameEditorLabel = new Label("THIS IS WHERE THE HEADERPANEL WILL GO");
    root.setTop(gameEditorLabel);
    root.setLeft(new VBox(editGridButton));
    root.setCenter(sortedGamesPanel = new SortedGamesPanel());
    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }
  private void refreshScene() {
//    root.
    setScene(new Scene(root));
    setText();
    setTheme();
  }

  @Override
  public void setText() {

  }
}
