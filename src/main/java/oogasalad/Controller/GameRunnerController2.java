package oogasalad.Controller;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneFE;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneVisual;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Piece;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;

import java.io.FileInputStream;
import java.util.*;

public class GameRunnerController2 implements GameController {

    private static final int SCREEN_WIDTH = 1080;
    private static final int SCREEN_HEIGHT = 700;

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, DropZoneFE> dropZones = new HashMap<>();
    private final Map<String, Piece> pieces = new HashMap<>();

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
    @Override
    public void select(String id) {
        if (clickable.contains(id)) {
            game.clickPiece(id);
        }
    }

    @Override
    public void addDropZone(GameRunnerController.DropZoneParameters params) {
        // String id, int x, int y, int height, int width

        //dropZone.getStyleClass().add("dropzone");
        String id = params.id();
        int x = params.x();
        int y = params.y();
        int width = params.width();
        int height = params.height();
        DropZoneFE newdrop = new DropZoneFE(id, width, height, x,y,this);

        nodes.put(params.id(),newdrop.getVisual());
        dropZones.put(params.id(),newdrop);
        root.getChildren().add(newdrop.getVisual());
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

        Piece p = new Piece(id,this,image,size);
        DropZoneFE dz = dropZones.get(dropZoneID);
        p.moveToDropZoneXY(dz.getDropZoneCenter());
        Node n = p.getNode();

        //piece.getChildren().add(imgv);
        //((StackPane) nodes.get(dropZoneID)).getChildren().add(piece);

        //dz.addPieceToDropZone(piece);
        nodes.put(id, p.getPieceBox());
        pieces.put(id,p);
        pieceToDropZoneMap.put(id, dropZoneID);
        root.getChildren().add(p.getPieceBox());
    }

    @Override
    public void setClickable(List<String> ids) {
        System.out.println("clickables:");
        System.out.println(ids);
        clearClickables();

        clickable.addAll(ids);
        for (String id : ids){
            Node gameobject = nodes.get(id);
            if (gameobject instanceof DropZoneVisual){
                DropZoneVisual dzv = (DropZoneVisual) gameobject;
                dzv.selected();
            }
            else {
                gameobject.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            }
        }
    }

    @Override
    public void movePiece(String id, String dropZoneID) {
        String dzid = pieceToDropZoneMap.get(id);
        DropZoneFE olddz = dropZones.get(dzid);
        DropZoneFE newdz = dropZones.get(dropZoneID);

        //Piece p = pieces.get(id);
        //p.moveToDropZoneXY(newdz.getDropZoneCenter());


        //newdz.addPieceToDropZone(nodes.get(id));
        //olddz.removePieceFromDropZone(nodes.get(id));
        //((HBox) nodes.get(dzid)).getChildren().remove(nodes.get(id));
        //((HBox) nodes.get(dropZoneID)).getChildren().add(nodes.get(id));
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
            Node gameobject = nodes.get(id);
            if (gameobject instanceof DropZoneVisual){
                DropZoneVisual dzv = (DropZoneVisual) gameobject;
                dzv.deselected();
            }
            else {
                gameobject.setStyle("");
            }
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

