package oogasalad.frontend.scenes;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Board;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.BoardPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {

  private Board board;
  private String playerTurn;
  private Label textInstructions;
  private BorderPane root;
  private GridPane boardPane;
  private VBox textVBOX;
  private String instruction;
  private GameRunnerController gameRunnerController;

  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();

    gameRunnerController = new GameRunnerController(this);

    boardPane = gameRunnerController.initializeBoard();
    VBox boardVBOX = new VBox(boardPane);
    boardVBOX.setAlignment(Pos.CENTER);
    root.setCenter(boardVBOX);

    ArrayList<Node> pieces = gameRunnerController.initializePieces();
    System.out.println(pieces);
    root.getChildren().addAll(pieces);

    //textInstructions = new Label();
    //initializeText();
    //refreshText();

    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }
  public Point2D getNodeXYOnGrid(Node node){
    return boardPane.sceneToLocal(node.getLayoutX(), node.getLayoutY());
  }

  private void refreshInstructions() {
    textInstructions.setText(instruction);
  }

  private void refreshText() {
    refreshInstructions();
    root.setBottom(textVBOX);
  }

  private void initializeText() {
    textVBOX = new VBox(10);
    //parseResponse(gameRunnerController.initialInstruction());
    textVBOX.getChildren().addAll(textInstructions);
  }

  private void runEnteredText(TextField textField) {
    String inputText = textField.getText(); // Get the text from the TextField
    //parseResponse(gameRunnerController.userResponds(inputText));
    refreshInstructions();
    textField.clear();
  }

  private void runOnClick(double x, double y) {
    System.out.println("X Y:");
    System.out.println(x);
    System.out.println(y);
    //Board.BoardXY boardXY = board.boardXYofClick(x, y);
    //int boardX = boardXY.x();
    //int boardY = boardXY.y();
    //System.out.println("board:");
    //System.out.println(boardX);
    //System.out.println(boardY);
    //String inputText = boardY + "," + boardX;
    //parseResponse(gameRunnerController.userResponds(inputText));
    //refreshInstructions();
    //textField.clear();
  }

  private void parseGrid(String gridString) {
    String[] rows = gridString.split("\n");
    for (int i = 0; i < rows.length; i++) {
      String row = rows[i];
      for (int j = 0; j < row.length(); j++) {
        String pieceName = row.substring(j, j + 1);
        if (pieceName.equals("-")) {
          continue;
        }
        //board.addPiece(i, j, pieceName);
      }
    }

  }

  @Override
  public void setText() {
  }
}
