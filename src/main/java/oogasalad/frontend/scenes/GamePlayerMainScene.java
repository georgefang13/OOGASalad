package oogasalad.frontend.scenes;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.Controller.GameRunnerController;

import java.util.Collection;
import java.util.Objects;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {
  private Label textInstructions;
  private BorderPane root;
  private GridPane boardPane;
  private GameRunnerController gameRunnerController;
  private String gameName;
  public static final String GAME_STYlE_FILE_PATH = "frontend/css/simpleGameView.css";
  private final String MODAL_STYLE_SHEET = Objects
          .requireNonNull(getClass().getClassLoader().getResource(GAME_STYlE_FILE_PATH))
          .toExternalForm();

  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();

    gameName = panelController.getSceneController().getWindowController().getData().toString();
    System.out.println(gameName);

    gameRunnerController = new GameRunnerController(gameName);

    Button undoButton = new Button("Undo");
    gameRunnerController.assignUndoButtonAction(undoButton);

    root.getChildren().addAll(gameRunnerController.getGameObjectVisuals());
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
