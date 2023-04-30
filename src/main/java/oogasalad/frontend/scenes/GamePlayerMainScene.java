package oogasalad.frontend.scenes;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import oogasalad.Controller.GameRunnerController;

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

    gameRunnerController = new GameRunnerController(root,gameName);

    /*
    boardPane = gameRunnerController.getBoardVisual();
    VBox boardVBOX = new VBox(boardPane);
    boardVBOX.setAlignment(Pos.CENTER);
    root.setCenter(boardVBOX);


    ArrayList<Node> pieces = gameRunnerController.initializePieces();
    System.out.println(pieces);
    root.getChildren().addAll(pieces);
=======
    System.out.println(panelController.getSceneController().getWindowController().getData().toString());

//    gameRunnerController = new GameRunnerController(this, sceneController.getWindowController().getData().toString());
//    boardPane = gameRunnerController.getBoardVisual();
//    VBox boardVBOX = new VBox(boardPane);
//    boardVBOX.setAlignment(Pos.CENTER);
//    root.setCenter(boardVBOX);
//
//    ArrayList<Node> pieces = gameRunnerController.initializePieces();
//    System.out.println(pieces);
//    root.getChildren().addAll(pieces);

    root.setCenter(new Label("Hello World"));
>>>>>>> 61d2e67fb4369949e66ba37513777f21b7d77aa4

     */
    Scene scene = new Scene(root);
    scene.getStylesheets().add(MODAL_STYLE_SHEET);
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
