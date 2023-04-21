package oogasalad.Controller;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Board;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.BoardPiece;
import oogasalad.frontend.scenes.GamePlayerMainScene;
import oogasalad.gamerunner.backend.fsm.FSMExample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameRunnerController {

    private FSMExample fsmExample;
    private GamePlayerMainScene gamePlayerMainScene;

    private String playerTurn;
    private Board board;
    private Map<Integer,BoardPiece> pieceMap;

    public GameRunnerController(GamePlayerMainScene gamePlayerMainScene) {
        this.gamePlayerMainScene = gamePlayerMainScene;
        fsmExample = new FSMExample();
        pieceMap = new HashMap<>();
    }

    public String userResponds(String enteredText) {
        String backendResponse = fsmExample.run(enteredText);
        return backendResponse;
    }

    public String initialInstruction() {
        String response = fsmExample.getInstruction();
        return parseResponse(response);
    }

    public GridPane initializeBoard() {
        //Will load these from backend file somehow or when created via modal
        int height = 3;
        int width = 3;
        board = new Board(height, width);
        return board.getBoardVisual();
    }
    private String parseResponse(String response) {
        String[] splitResponse = response.split("Turn: ");
        String[] secondSplit = splitResponse[1].split("\n");
        playerTurn = playerDoubleStringtoName(secondSplit[0]);
        System.out.print("player turn: ");
        System.out.println(playerTurn);
        return "Turn: " + playerTurn + secondSplit[1];
    }

    private String playerDoubleStringtoName(String doubleString){ //FILE
        double dub = Double.parseDouble(doubleString);
        int playeridx = Integer.valueOf((int) dub);
        String[] players = {"X","O"};
        return players[playeridx];
    }

    public ArrayList<Node> initializePieces() {
        ArrayList<Node> pieceNodes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int pieceID = i;
            BoardPiece piece = new BoardPiece(pieceID,this);
            piece.setSize(0.2);
            pieceMap.put(pieceID,piece);
            pieceNodes.add(piece.getNode());
        }
        return pieceNodes;
    }
    public void updatePieceMove(int id) {
        BoardPiece piece = pieceMap.get(id);
        Board.BoardXY boardXY = getPieceBoardLocation(piece.getNode());
        int boardX = boardXY.x();
        int boardY = boardXY.y();
        if ((boardX != -1) && (boardY != -1)){
            if (playerTurn == replaceWithFileLoaderThatAssignIDtoPiece(id)) {
                String userInput = boardY + "," + boardX;
                System.out.println(userInput);
                String fsmReturn = userResponds(userInput);
                String newInstruction = parseResponse(fsmReturn);
                gamePlayerMainScene.refreshInstructions(newInstruction);
            }
            else {
                String newInstruction = "ITS NOT YOUR MOVE!!!";
                gamePlayerMainScene.refreshInstructions(newInstruction);
            }
        }
    }
    private String replaceWithFileLoaderThatAssignIDtoPiece(int id){
        if (id < 6){
            return "O";
        }
        else {
            return "X";
        }
    }
    private Board.BoardXY getPieceBoardLocation(Node node) {
        Point2D gridPaneXY = gamePlayerMainScene.getNodeXYOnGrid(node);
        Board.BoardXY boardXY = board.boardXYofNode(gridPaneXY);
        return boardXY;
    }
}
