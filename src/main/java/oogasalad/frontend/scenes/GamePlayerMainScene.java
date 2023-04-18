package oogasalad.frontend.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.objects.Board;
import oogasalad.frontend.objects.BoardPiece;
import oogasalad.gamerunner.backend.fsm.GameRunnerController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {

  private Board board;
  private List<BoardPiece> pieces;
  private String playerTurn;
  private Label textInstructions;
  private BorderPane root;
  private VBox textVBOX;
  private String instruction;
  private GameRunnerController gameRunnerController;

  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();
    root.setOnMouseClicked(event -> pieceBeingMoved());

    gameRunnerController = new GameRunnerController();

    //Will load these from backend file somehow or when created via modal
    int height = 3;
    int width = 3;

    board = new Board(height, width);
    initializeBoard();

    pieces = initializePieces();

    textInstructions = new Label();

    initializeText();
    refreshText();

    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }

  private void pieceBeingMoved() {
    BoardPiece activepiece = null;
    for (int i = 0; i < pieces.size(); i++) {
      BoardPiece piece = pieces.get(i);
      if (piece.getActive()) {
        activepiece = piece;
      }
    }
    if (!activepiece.equals(null)){
      BoardPiece finalActivepiece = activepiece;
      root.setOnMouseReleased(event -> {
        placePieceOnGrid(finalActivepiece);
      });
    }
  }

  private void placePieceOnGrid(BoardPiece activePiece){
    if (activePiece.getName().equals(playerTurn)){
      activePiece.
    }
  }

  private void initializeBoard() {
    GridPane boardPane = board.getBoardVisual();
    boardPane.setOnMouseClicked((MouseEvent event) -> {
      double x = event.getX();
      double y = event.getY();
      runOnClick(x, y);
    });
    VBox boardVBOX = new VBox(boardPane);
    boardVBOX.setAlignment(Pos.CENTER);
    root.setCenter(boardVBOX);
  }
  private ArrayList<BoardPiece> initializePieces() {
    ArrayList<BoardPiece> newPieces = new ArrayList<>();
    BoardPiece x1 = new BoardPiece("X",1);
    x1.setSize(30);
    newPieces.add(x1);
    BoardPiece O1 = new BoardPiece("O",1);
    O1.setSize(30);
    newPieces.add(O1);
    root.getChildren().addAll(x1.getNode(),O1.getNode());
    return newPieces;
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
    parseResponse(gameRunnerController.initialInstruction());
    textVBOX.getChildren().addAll(textInstructions);
  }

  private void runEnteredText(TextField textField) {
    String inputText = textField.getText(); // Get the text from the TextField
    parseResponse(gameRunnerController.userResponds(inputText));
    refreshInstructions();
    textField.clear();
  }

  private void runOnClick(double x, double y) {
    System.out.println("X Y:");
    System.out.println(x);
    System.out.println(y);
    Board.BoardXY boardXY = board.boardXYofClick(x, y);
    int boardX = boardXY.x();
    int boardY = boardXY.y();
    System.out.println("board:");
    System.out.println(boardX);
    System.out.println(boardY);
    String inputText = boardY + "," + boardX;
    parseResponse(gameRunnerController.userResponds(inputText));
    refreshInstructions();
    //textField.clear();
  }

  private void parseResponse(String response) {
    String[] splitResponse = response.split("Turn: ");
    playerTurn = splitResponse[1].split("\n")[0];
    System.out.print("player turn: ");
    System.out.println("playerTurn");
    instruction = "Turn: " + splitResponse[1];
    String gridString = splitResponse[0];
    //parseGrid(gridString);
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
        board.addPiece(i, j, pieceName);
      }
    }

  }

  @Override
  public void setText() {
  }
}
