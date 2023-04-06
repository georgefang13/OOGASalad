package oogasalad.windowscene;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GameEditorMainScene extends AbstractScene {

  public GameEditorMainScene(AbstractWindow window) {
    super(window);
  }

  @Override
  public Scene makeScene() {
    BorderPane root = new BorderPane();
    root.setTop(new Label("Game Editor - HOME GAME CHOOSER SCENE"));
    root.setLeft(new VBox(new Button("Edit Grid")));
    Button goButton = new Button("SELECT GAME"); //TODO: Properties File

    String newsceneID = "new";
    getWindow().addAndLinkScene(GameEditorWindow.WindowScenes.EDITOR_SCENE,newsceneID);

    goButton.setOnAction(e -> getWindow().switchToScene(newsceneID));
    root.setLeft(new VBox(goButton));
    setScene(new Scene(root));
    return getScene();
  }

}
