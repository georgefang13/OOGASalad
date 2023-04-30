package oogasalad.frontend.scenes;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.managers.NodeRemovedListener;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {
  private Label textInstructions;
  private BorderPane root;
  private GridPane boardPane;
  private GameRunnerController gameRunnerController;

  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();

    String gameName = panelController.getSceneController().getWindowController().getData().toString();
    System.out.println(gameName);

    gameRunnerController = new GameRunnerController(gameName,root);

    Button undoButton = new Button("Undo");
    gameRunnerController.assignUndoButtonAction(undoButton);

    ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
    gameObjectVisuals.addListener(new NodeRemovedListener(root));

    root.getChildren().addAll(gameObjectVisuals);
    root.getChildren().add(undoButton);

    Scene scene = new Scene(root);
    setScene(scene);
    setText();
    setTheme();
    return getScene();
  }

  public Point2D getNodeXYOnGrid(Node node){
    return boardPane.sceneToLocal(node.getTranslateX(), node.getTranslateY());
  }

  @Override
  public void setText() {
  }
}
