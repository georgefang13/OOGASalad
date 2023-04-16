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
import oogasalad.gamerunner.backend.fsm.GameRunnerController;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {

  private Board board;
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

    gameRunnerController = new GameRunnerController();

    //Will load these from backend file somehow or when created via modal
    int height = 3;
    int width = 3;

    board = new Board(height, width);
    initializeBoard();

    textInstructions = new Label();

    initializeText();
    refreshText();

    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
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

  private void refreshInstructions() {
    textInstructions.setText(instruction);
  }

  private void refreshText() {
    refreshInstructions();
    root.setBottom(textVBOX);
  }

  private void initializeText() {
    textVBOX = new VBox(10);
    //TextField textField = new TextField();
    //Button submitButton = new Button("Submit");
    //textField.setPromptText("Enter text:"); // Set prompt text
    //submitButton.setOnAction(event -> runEnteredText(textField));
    parseResponse(gameRunnerController.initialInstruction());
    //textVBOX.getChildren().addAll(textInstructions,textField, submitButton);
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
    String[] splitResponse = response.split("Turn:");
    instruction = "Turn:" + splitResponse[1];
    String gridString = splitResponse[0];
    parseGrid(gridString);
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
