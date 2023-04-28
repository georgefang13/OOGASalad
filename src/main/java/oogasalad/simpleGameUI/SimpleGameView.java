package oogasalad.simpleGameUI;

import javafx.application.Application;
import javafx.application.Platform;
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
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

        String directory = "data/games/checkers";

        loadGame(directory);

        game = new Game(this, directory, 2, true);

        undoButton.setOnAction(e -> game.undoClickPiece());
        root.getChildren().add(undoButton);

//        game.startGame();

//        game.createOnlineGame();
        game.joinOnlineGame("525");

        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadGame(String directory) throws FileNotFoundException {
        directory = directory + "/frontend";
        loadDropZones(directory);
        loadPieces(directory);
    }

    private void loadDropZones(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/layout.json");
        for (String id : fm.getTagsAtLevel()){
            int x = Integer.parseInt(fm.getString(id, "x"));
            int y = Integer.parseInt(fm.getString(id, "y"));
            int height = Integer.parseInt(fm.getString(id, "height"));
            int width = Integer.parseInt(fm.getString(id, "width"));
            addDropZone(new GameRunnerController.DropZoneParameters(id, x, y, height, width));
        }
    }

    private void loadPieces(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/objects.json");
        for (String id : fm.getTagsAtLevel()){
            String image = fm.getString(id, "image");
            image = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + image;
            String dropZoneID = fm.getString(id, "location");
            double size = Double.parseDouble(fm.getString(id, "size"));
            addPiece(id, image, dropZoneID, size);
        }
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
//        imgv.setFitWidth(size);
//        imgv.setFitHeight(size);

        double h = img.getHeight();
        double w = img.getWidth();
        if (h > w) {
            w = size * w / h;
            h = size;
        } else {
            h = size * h / w;
            w = size;
        }
        imgv.setFitWidth(w);
        imgv.setFitHeight(h);

        HBox piece = new HBox();
        piece.getChildren().add(imgv);
        piece.setPrefHeight(h);
        piece.setPrefWidth(w);
        piece.setMaxHeight(h);
        piece.setMaxHeight(w);

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
//        System.out.println(id + " " + dropZoneID);
        String dzid = pieceToDropZoneMap.get(id);
        Platform.runLater(() -> {
            ((HBox) nodes.get(dzid)).getChildren().remove(nodes.get(id));
            ((HBox) nodes.get(dropZoneID)).getChildren().add(nodes.get(id));
            pieceToDropZoneMap.put(id, dropZoneID);
        });
    }

    @Override
    public void removePiece(String id) {
        String dzid = pieceToDropZoneMap.get(id);
        Platform.runLater(() -> {
            Node n = nodes.get(id);
            HBox dz = (HBox) nodes.get(dzid);
            pieceToDropZoneMap.remove(id);
            dz.getChildren().remove(n);
            root.getChildren().remove(n);
        });
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
        int w = (int) oldimg.getFitWidth();
        int h = (int) oldimg.getFitHeight();

        imgv.setFitWidth(w);
        imgv.setFitHeight(h);

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
}
