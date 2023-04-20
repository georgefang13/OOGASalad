package oogasalad.Controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import oogasalad.frontend.objects.Board;
import oogasalad.frontend.objects.BoardPiece;
import oogasalad.gamerunner.backend.fsm.FSMExample;

import java.util.ArrayList;

public class GameRunnerController {

    private FSMExample fsmExample;
    String playerTurn;

    public GameRunnerController() {
        fsmExample = new FSMExample();
    }

    public String userResponds(String enteredText) {
        String backendResponse = fsmExample.run(enteredText);
        return backendResponse;
    }

    public String initialInstruction() {
        return fsmExample.getInstruction();
    }

    public VBox initializeBoard() {
        //Will load these from backend file somehow or when created via modal
        int height = 3;
        int width = 3;
        Board board = new Board(height, width);
        GridPane boardPane = board.getBoardVisual();
        VBox boardVBOX = new VBox(boardPane);
        boardVBOX.setAlignment(Pos.CENTER);
        return boardVBOX;
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
        ArrayList<BoardPiece> newPieces = new ArrayList<>();
        ArrayList<Node> pieceNodes = new ArrayList<>();
        BoardPiece x1 = new BoardPiece("X",1,this);
        x1.setSize(30);
        newPieces.add(x1);
        BoardPiece O1 = new BoardPiece("O",1,this);
        O1.setSize(30);
        newPieces.add(O1);
        BoardPiece x2 = new BoardPiece("X",2,this);
        x2.setSize(30);
        newPieces.add(x1);
        BoardPiece O2 = new BoardPiece("O",2,this);
        O2.setSize(30);
        newPieces.add(O1);
        BoardPiece x3 = new BoardPiece("X",3,this);
        x3.setSize(30);
        newPieces.add(x1);
        BoardPiece O3 = new BoardPiece("O",3,this);
        O3.setSize(30);
        newPieces.add(O1);
        for (BoardPiece piece:newPieces) {
            pieceNodes.add(piece.getNode());
        }
        return pieceNodes;
    }
}
