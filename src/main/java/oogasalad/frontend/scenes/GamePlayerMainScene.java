package oogasalad.frontend.scenes;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import oogasalad.Controller.GameController;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.managers.NodeRemovedListener;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {
  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    BorderPane root = new BorderPane();

    String gameName = panelController.getSceneController().getWindowController().getData().toString();
    System.out.println(gameName);

    GameController gameRunnerController = new GameRunnerController(gameName);

    ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
    gameObjectVisuals.addListener(new NodeRemovedListener(root));
    root.getChildren().addAll(gameObjectVisuals);

    //Button undoButton = new Button("Undo");
    //gameRunnerController.assignUndoButtonAction(undoButton);
    //root.getChildren().add(undoButton);

    Scene scene = new Scene(root);
    setScene(scene);
    setText();
    setTheme();
    return getScene();
  }

  @Override
  public void setText() {
  }
}
