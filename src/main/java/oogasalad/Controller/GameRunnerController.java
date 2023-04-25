package oogasalad.Controller;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
//import oogasalad.frontend.components.gameObjectComponent.GameObject;
import javafx.scene.layout.HBox;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Board;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Piece;
import oogasalad.frontend.scenes.GamePlayerMainScene;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneCell;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class GameRunnerController implements GameController {
    private Game game;
    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, String> pieceToDropZoneMap = new HashMap<>();
    private HashSet<String> clickable = new HashSet<>();
    public static final String GAME_STYlE_FILE_PATH = "frontend/css/simpleGameView.css";
    private final String MODAL_STYLE_SHEET = Objects
            .requireNonNull(getClass().getClassLoader().getResource(GAME_STYlE_FILE_PATH))
            .toExternalForm();

    private Board board;







    private GamePlayerMainScene gamePlayerMainScene;

    private String playerTurn;

    private Map<Integer, Piece> pieceMap;
    private Map<String, GameObject> backendPieces;
    private Map<String, DropZone> backendDropZones;
    String directory;

    public GameRunnerController(GamePlayerMainScene gamePlayerMainScene) {
        this.gamePlayerMainScene = gamePlayerMainScene;
        //this.gamePlayerMainScene.makeScene().getStylesheets().add(MODAL_STYLE_SHEET);
        directory = "data/games/tictactoe";
        int numPlayers = 2; //hardcoded read from file
        game = new Game(this,directory,numPlayers);

        initializeBoard();
        pieceMap = new HashMap<>();

    }

    private void initializeBoard() {
        int height = 3;
        int width = 3;
        board = new Board(height, width);
    }

    public GridPane getBoardVisual() {
        return board.getBoardVisual();
    }

    @Override
    public void addDropZone(DropZoneParameters params){
        // String id, int x, int y, int height, int width
        String id = params.id();
        int x = params.x();
        int y = params.y();
        int height = params.height();
        DropZoneCell DZfrontend = new DropZoneCell(id,height);
        board.addBoardCell(DZfrontend, x, y);
    }
    @Override
    public void addPiece(String id, String image, String dropZoneID, double size) {
        Piece piece;
        try {
            piece = new Piece(id ,this, new FileInputStream(image));
        } catch (Exception e) {
            System.out.println("Image " + image + " not found");
            return;
        }

        piece.setSize(0.2);
        pieceMap.put(pieceID,piece);
        pieceNodes.add(piece.getNode());


        Image img;
        try {
            img = new Image(new FileInputStream(image));
        } catch (Exception e) {
            System.out.println("Image " + image + " not found");
            return;
        }

        ImageView imgv = new ImageView(img);
        imgv.setFitWidth(size);
        imgv.setFitHeight(size);

        HBox piece = new HBox();
        piece.getChildren().add(imgv);
        piece.setPrefHeight(size);
        piece.setPrefWidth(size);
        piece.setMaxHeight(size);
        piece.setMaxHeight(size);

        piece.setOnMouseClicked(e -> select(id));

        ((HBox) nodes.get(dropZoneID)).getChildren().add(piece);
        nodes.put(id, piece);

        pieceToDropZoneMap.put(id, dropZoneID);
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

    @Override
    public void setClickable(List<String> ids){

    }

    @Override
    public void movePiece(String id, String dropZoneID) {


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
