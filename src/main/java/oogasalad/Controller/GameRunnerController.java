package oogasalad.Controller;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
//import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Board;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Piece;
import oogasalad.frontend.scenes.GamePlayerMainScene;
import oogasalad.gamerunner.backend.Game;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameRunnerController {
    private Game game;
    private GamePlayerMainScene gamePlayerMainScene;

    private String playerTurn;
    private Board board;
    private Map<Integer, Piece> pieceMap;
    private Map<String, GameObject> backendPieces;
    private Map<String, DropZone> backendDropZones;
    String directory;

    public GameRunnerController(GamePlayerMainScene gamePlayerMainScene) {
        this.gamePlayerMainScene = gamePlayerMainScene;

        directory = "data/tictactoe";
        int numPlayers = 2; //hardcoded read from file

        game = new Game(this,directory,numPlayers);
        pieceMap = new HashMap<>();
    }

    public String userResponds(String pieceID, String dropzoneID) {
        GameObject piece = backendPieces.get(pieceID);
        DropZone dropZone = backendDropZones.get(dropzoneID);
        game.movePiece(piece,dropZone,pieceID);
        return "pass";
    }

    public String initialInstruction() {
        //String response = fsmExample.getInstruction();
        return "pass"; //parseResponse(response);
    }

    public GridPane initializeBoard() {
        //Will load these from backend file somehow or when created via modal
        int height = 3;
        int width = 3;
        board = new Board(height, width);

        return board.getBoardVisual();
    }
    public void initializeDropZone(DropZoneParameters params){


    }

    public record DropZoneParameters(String id, int x, int y, int height, int width){}

    private void parseDropZoneLayout() throws FileNotFoundException {
        FileManager DZparser = new FileManager(directory + "/layout.json");
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
            Piece piece = new Piece(pieceID,this);
            piece.setSize(0.2);
            pieceMap.put(pieceID,piece);
            pieceNodes.add(piece.getNode());
        }
        return pieceNodes;
    }
    public void updatePieceMove(int id) {
        Piece piece = pieceMap.get(id);
        Board.BoardXY boardXY = getPieceBoardLocation(piece.getNode());
        int boardX = boardXY.x();
        int boardY = boardXY.y();
        if ((boardX != -1) && (boardY != -1)){
            if (playerTurn == replaceWithFileLoaderThatAssignIDtoPiece(id)) {
                String userInput = boardY + "," + boardX;
                System.out.println(userInput);
                //String fsmReturn = userResponds(userInput);
                //String newInstruction = parseResponse(fsmReturn);
                gamePlayerMainScene.refreshInstructions("pass");
            }
            else {
                String newInstruction = "ITS NOT YOUR MOVE!!!";
                gamePlayerMainScene.refreshInstructions(newInstruction);
                piece.goBack();
            }
        }
        piece.acceptDrag();
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
