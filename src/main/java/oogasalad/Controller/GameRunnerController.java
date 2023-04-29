package oogasalad.Controller;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.AbstractSelectableVisual;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneFE;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.GameRunnerObject;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Piece;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;

import java.io.FileInputStream;
import java.util.*;

public class GameRunnerController implements GameController {
    private final Map<String, GameRunnerObject> gameObjects = new HashMap<>();
    private final Map<String, Node> nodes = new HashMap<>();
    //private final Map<String, DropZoneFE> dropZones = new HashMap<>();
    private final Map<String, String> pieceToDropZoneMap = new HashMap<>();
    private BorderPane root;
    private final HashSet<String> clickable = new HashSet<>();
    private Game game;

    public GameRunnerController(BorderPane root, String gameName) {
        this.root = root;
        String directory = "data/games/"+gameName;
        int numPlayers = 2;
        game = new Game(this,directory,numPlayers);
    }
    @Override
    public void select(String id) {
        if (clickable.contains(id)) {
            game.clickPiece(id);
        }
    }

    @Override
    public void addDropZone(GameController.DropZoneParameters params) {
        DropZoneFE dropZone = new DropZoneFE(params.id(), params.width(), params.height(), params.x(),params.y(),this);
        nodes.put(params.id(),dropZone.getNode());
        gameObjects.put(params.id(),dropZone);
        //dropZones.put(params.id(),dropZone);
        root.getChildren().add(dropZone.getNode());
    }

    @Override
    public void addPiece(String id, String imagePath, String dropZoneID, double size) {
        Piece piece = new Piece(id,this, imagePath, size);
        //DropZoneFE dz = dropZones.get(dropZoneID);
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
        nodes.put(id, piece.getNode());
        pieceToDropZoneMap.put(id, dropZoneID);
        root.getChildren().add(piece.getNode());
    }

    @Override
    public void setClickable(List<String> ids) {
        clearClickables();
        clickable.addAll(ids);
        for (String id : ids){
            AbstractSelectableVisual gameObjectVisual = (AbstractSelectableVisual) nodes.get(id);
            gameObjectVisual.showClickable();
        }
    }

    @Override
    public void movePiece(String pieceID, String dropZoneID) {
        //DropZoneFE dropZone = dropZones.get(dropZoneID);
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        //pieces.get(pieceID).moveToDropZoneXY(dropZone.getDropZoneCenter());
        pieceToDropZoneMap.put(pieceID, dropZoneID);
    }

    @Override
    public void removePiece(String pieceID) {
        pieceToDropZoneMap.remove(pieceID);
        root.getChildren().remove(nodes.get(pieceID));
        nodes.remove(pieceID);
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

        AbstractSelectableVisual gameObject = (AbstractSelectableVisual) nodes.get(id);
        gameObject.updateVisual(imgv);
    }

    private void clearClickables(){
        for (String id : clickable){
            AbstractSelectableVisual gameObjectVisual = (AbstractSelectableVisual) nodes.get(id);
            gameObjectVisual.showUnclickable();
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

