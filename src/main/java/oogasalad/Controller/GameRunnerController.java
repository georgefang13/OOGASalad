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
        return fsmExample.getInstruction();
    }

    public GridPane initializeBoard() {
        //Will load these from backend file somehow or when created via modal
        int height = 3;
        int width = 3;
        board = new Board(height, width);
        return board.getBoardVisual();
    }
    private void parseResponse(String response) {
        String[] splitResponse = response.split("Turn: ");
        playerTurn = playerDoubleStringtoName(splitResponse[1].split("\n")[0]);
        System.out.print("player turn: ");
        System.out.println(playerTurn);
        //instruction = "Turn: " + splitResponse[1];
        //String gridString = splitResponse[0];
        //parseGrid(gridString);
    }

    private String playerDoubleStringtoName(String doubleString){
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
            piece.setSize(30);
            pieceMap.put(pieceID,piece);
            pieceNodes.add(piece.getNode());
        }
        return pieceNodes;
    }
    public void updatePieceMove(int id) {
        BoardPiece piece = pieceMap.get(id);
        Board.BoardXY boardXY = getPieceBoardLocation(piece.getNode());
        String userInput = boardXY.y() + "," + boardXY.x();
        userResponds(userInput);
    }
    private Board.BoardXY getPieceBoardLocation(Node node) {
        Point2D gridPaneXY = gamePlayerMainScene.getNodeXYOnGrid(node);
        Board.BoardXY boardXY = board.boardXYofNode(gridPaneXY);
        return boardXY;
    }
}
