package oogasalad.simpleGameUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import oogasalad.Controller.GameRunnerController;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;

import java.io.FileInputStream;
import java.util.*;

public class SimpleGameView extends Application implements GameController {

    private static final int SCREEN_WIDTH = 1080;
    private static final int SCREEN_HEIGHT = 700;

    private final Map<String, Node> nodes = new HashMap<>();

    private final Map<String, String> pieceToDropZoneMap = new HashMap<>();

    private final Group root = new Group();

    private final HashSet<String> clickable = new HashSet<>();

    private final Button undoButton = new Button("Undo");

    private Game game;

    public static final String GAME_STYlE_FILE_PATH = "frontend/css/simpleGameView.css";
    private final String MODAL_STYLE_SHEET = Objects
            .requireNonNull(getClass().getClassLoader().getResource(GAME_STYlE_FILE_PATH))
            .toExternalForm();
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add(MODAL_STYLE_SHEET);

        game = new Game(this, "data/games/checkers2", 2);

        undoButton.setOnAction(e -> game.undoClickPiece());
        root.getChildren().add(undoButton);

        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
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
            nodes.get(id).setStyle("-fx-border-color: red; -fx-border-width: 1px;");
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

    private void clearClickables(){
        for (String id : clickable){
            nodes.get(id).setStyle("");
        }
        clickable.clear();
    }
}
