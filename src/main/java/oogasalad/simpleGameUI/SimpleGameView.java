package oogasalad.simpleGameUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oogasalad.gamerunner.backend.Game;
import oogasalad.Controller.GameController;
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
    private String directory;
    private String code;

    public static final String GAME_STYlE_FILE_PATH = "frontend/css/light.css";
    private final String MODAL_STYLE_SHEET = Objects
            .requireNonNull(getClass().getClassLoader().getResource(GAME_STYlE_FILE_PATH))
            .toExternalForm();
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        scene.getStylesheets().add(MODAL_STYLE_SHEET);

        directory = "data/games/connect4";

        showStartScreen();

        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }

    private void showStartScreen() {
        Button btn = new Button("Start Local Game");
        btn.setOnAction(e -> startGame("local"));
        Button btn2 = new Button("Create Online Game");
        btn2.setOnAction(e -> startGame("create"));
        TextField codeField = new TextField();
        codeField.setPromptText("Enter Code");
        Button btn3 = new Button("Join Online Game");
        btn3.setOnAction(e -> {
            code = codeField.getText();
            startGame("join");
        });
        VBox hbox = new VBox(btn, btn2, codeField, btn3);
        root.getChildren().add(hbox);
    }


    private void startGame(String type) {

        game.startGame();

        root.getChildren().retainAll();
        try {
            loadGame(directory);

            game = new Game(this, directory, 2,  !type.equals("local"));

            undoButton.setOnAction(e -> game.undoClickPiece());
            root.getChildren().add(undoButton);

            switch (type) {
                case "local" -> game.startGame();
                case "create" -> game.createOnlineGame();
                case "join" -> game.joinOnlineGame(code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            addDropZone(new GameController.DropZoneParameters(id, null,null,x, y, height, width));
        }
    }

    private void loadPieces(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/objects.json");
        for (String id : fm.getTagsAtLevel()){
            String image = fm.getString(id, "image");
            image = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + image;
            String dropZoneID = fm.getString(id, "location");
            double size = Double.parseDouble(fm.getString(id, "size"));
            addPiece(id, image, dropZoneID, false, null, size, 0.0);
        }
    }
    @Override
    public void select(String id) {
        if (clickable.contains(id)) {
            game.clickPiece(id);
        }
    }

    @Override
    public boolean isObjectPlayable(String id) {
        return false;
    }

    @Override
    public ObservableList<Node> getGameObjectVisuals() {
        return null;
    }

    @Override
    public void addDropZone(GameController.DropZoneParameters params) {
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
    public void addPiece(String id, String image, String dropZoneID, boolean hasSelectImage, Object param, double height, double width) throws FileNotFoundException {
        double size = height;
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
            Node n = nodes.get(id);
            n.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
        }
    }

    @Override
    public void movePiece(String id, String dropZoneID) {
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

    @Override
    public void passGameId(String code) {
        Platform.runLater(() -> {
            Label label = new Label("Game ID: " + code);
            label.setLayoutX(100);
            label.setLayoutY(10);
            root.getChildren().add(label);
        });
    }

    private void clearClickables(){
        for (String id : clickable){
            nodes.get(id).setStyle("");
        }
        clickable.clear();
    }
}
