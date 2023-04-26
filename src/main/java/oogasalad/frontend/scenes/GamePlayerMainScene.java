package oogasalad.frontend.scenes;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import oogasalad.Controller.GameRunnerController;

import java.util.ArrayList;

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

  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();
    gameRunnerController = new GameRunnerController(this);

    ArrayList<Node> pieces = gameRunnerController.initializePieces();
    root.getChildren().addAll(pieces);

    /*
    boardPane = gameRunnerController.getBoardVisual();
    VBox boardVBOX = new VBox(boardPane);
    boardVBOX.setAlignment(Pos.CENTER);
    root.setCenter(boardVBOX);


    ArrayList<Node> pieces = gameRunnerController.initializePieces();
    System.out.println(pieces);
    root.getChildren().addAll(pieces);

     */

    setScene(new Scene(root));
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
