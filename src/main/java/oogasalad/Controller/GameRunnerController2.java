package oogasalad.Controller;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import oogasalad.Controller.GameRunnerController;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;

import java.io.FileInputStream;
import java.util.*;

public class GameRunnerController2 implements GameController {

    private static final int SCREEN_WIDTH = 1080;
    private static final int SCREEN_HEIGHT = 700;

    private final Map<String, Node> nodes = new HashMap<>();

    private final Map<String, String> pieceToDropZoneMap = new HashMap<>();

    private BorderPane root;

    private final HashSet<String> clickable = new HashSet<>();

    private final Button undoButton = new Button("Undo");

    private Game game;

    public static final String GAME_STYlE_FILE_PATH = "frontend/css/simpleGameView.css";
    private final String MODAL_STYLE_SHEET = Objects
            .requireNonNull(getClass().getClassLoader().getResource(GAME_STYlE_FILE_PATH))
            .toExternalForm();

    public GameRunnerController2(BorderPane root, String gameName) {
        this.root = root;
        String directory = "data/games/"+gameName;
        int numPlayers = 2;
        //initializeBoard();
        game = new Game(this,directory,numPlayers);
    }

    private void select(String id) {
        if (clickable.contains(id)) {
            game.clickPiece(id);
        }
    }

    @Override
    public void addDropZone(GameRunnerController.DropZoneParameters params) {
        // String id, int x, int y, int height, int width
        HBox dropZone = new HBox();
        dropZone.setPrefWidth(params.width());
        dropZone.setPrefHeight(params.height());
        dropZone.setLayoutX(params.x());
        dropZone.setLayoutY(params.y());
        dropZone.getStyleClass().add("dropzone");

        dropZone.setOnMouseClicked(e -> select(params.id()));
        nodes.put(params.id(), dropZone);
        root.getChildren().add(dropZone);
    }

    @Override
    public void addPiece(String id, String image, String dropZoneID, double size) {
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

    @Override
    public void setClickable(List<String> ids) {
        clearClickables();

        clickable.addAll(ids);
        for (String id : ids){
            Node n = nodes.get(id);
            n.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
    }

    @Override
    public void movePiece(String id, String dropZoneID) {
        String dzid = pieceToDropZoneMap.get(id);
        ((HBox) nodes.get(dzid)).getChildren().remove(nodes.get(id));
        ((HBox) nodes.get(dropZoneID)).getChildren().add(nodes.get(id));
        pieceToDropZoneMap.put(id, dropZoneID);
    }

    @Override
    public void removePiece(String id) {
        String dzid = pieceToDropZoneMap.get(id);
        Node n = nodes.get(id);
        HBox dz = (HBox) nodes.get(dzid);
        pieceToDropZoneMap.remove(id);
        dz.getChildren().remove(n);
        root.getChildren().remove(n);
    }

    @Override
    public void setObjectImage(String id, String imagePath) {
        Image img;
        try {
            img = new Image(new FileInputStream(imagePath));
        } catch (Exception e) {
            System.out.println("Image " + imagePath + " not found");
            return;
        }

        ImageView imgv = new ImageView(img);


        ImageView oldimg = (ImageView) ((HBox) nodes.get(id)).getChildren().get(0);
        int size = (int) oldimg.getFitWidth();

        imgv.setFitWidth(size);
        imgv.setFitHeight(size);

        HBox piece = (HBox) nodes.get(id);
        piece.getChildren().clear();
        piece.getChildren().add(imgv);
    }

    private void clearClickables(){
        for (String id : clickable){
            nodes.get(id).setStyle("");
        }
        clickable.clear();
    }

    public ArrayList<Node> getNodes(){
        ArrayList<Node> nodelist = new ArrayList<>();
        for (Node node: nodes.values()) {
            node.toBack();
            nodelist.add(node);
        }
        return nodelist;
    }
}

