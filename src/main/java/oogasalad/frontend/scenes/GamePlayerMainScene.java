package oogasalad.frontend.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.objects.Board;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.gamerunner.backend.fsm.FSMExample;
import oogasalad.gamerunner.backend.fsm.GameRunnerController;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {

  private Button playGameButton;
  private Label gamePlayerLabel;
  private Board board;
  private Label textInstructions;
  private BorderPane root;
  private VBox textVBOX;
  private String instruction;
  private GameRunnerController gameRunnerController;
  private String[][] stringGrid;

  public GamePlayerMainScene(SceneController sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();
    playGameButton = new Button();
    gamePlayerLabel = new Label();
    root.setTop(gamePlayerLabel);
    root.setLeft(new VBox(playGameButton));

    gameRunnerController = new GameRunnerController();

    //Will load these from backend file somehow or when created via modal
    int height = 3;
    int width = 3;

    board = new Board(height,width);
    stringGrid = new String[height][width];
    refreshBoard();

    textInstructions = new Label();

    initializeText();
    refreshText();

    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }
  private void refreshBoard(){
    VBox boardVBOX = new VBox(board.getBoardVisual());
    boardVBOX.setAlignment(Pos.CENTER);
    root.setCenter(boardVBOX);
  }
  private void refreshInstructions(){
    textInstructions.setText(instruction);
  }
  private void refreshText(){
    refreshInstructions();
    root.setBottom(textVBOX);
  }
  private void initializeText(){
    textVBOX = new VBox(10);
    TextField textField = new TextField();
    Button submitButton = new Button("Submit");
    textField.setPromptText("Enter text:"); // Set prompt text
    submitButton.setOnAction(event -> runEnteredText(textField));
    parseResponse(gameRunnerController.initialInstruction());
    textVBOX.getChildren().addAll(textInstructions,textField, submitButton);
  }
  private void runEnteredText(TextField textField){
    String inputText = textField.getText(); // Get the text from the TextField
    System.out.println("Input text: " + inputText); // Print the input text
    parseResponse(gameRunnerController.userResponds(inputText));
    refreshInstructions();
    textField.clear();
  }
  private void parseResponse(String response){
    String[] splitResponse = response.split("Turn:");
    instruction = "Turn:"+splitResponse[1];
    String gridString = splitResponse[0];
    parseGrid(gridString);
  }
  private void parseGrid(String gridString){
    System.out.print("inside parseGrid");
    String[] rows = gridString.split("\n");
    for (int i = 0; i < rows.length; i++) {
      String row = rows[i];
      System.out.print(row);
      System.out.print("\n");
      for (int j = 0; j < row.length(); j++) {
        String pieceName = row.substring(j,j+1);
        if (pieceName.equals("-")){
          continue;
        }
        board.addPiece(i,j,pieceName);
      }
    }

  }

  @Override
  public void setText() {
    playGameButton.setText(getPropertyManager().getText("GamePlayerMainScene.PlayGameButton"));
    gamePlayerLabel.setText(getPropertyManager().getText("GamePlayerMainScene.GamePlayerLabel"));
  }
}
