package oogasalad.Controller;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.*;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;

import java.io.FileInputStream;
import java.util.*;

public class GameRunnerController implements GameController {
    private final Map<String, GameRunnerObject> gameObjects = new HashMap<>();
    private final Map<String, String> pieceToDropZoneMap = new HashMap<>();
    private BorderPane root;
    private final HashSet<String> clickable = new HashSet<>();
    private Game game;

    public GameRunnerController(BorderPane root, String gameName) {
        this.root = root;
        String directory = "data/games/"+gameName;
        int numPlayers = 2;
        game = new Game(this,directory,numPlayers,false);
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
        gameObjects.put(params.id(),dropZone);
        root.getChildren().add(dropZone.getNode());
    }

    @Override
    public void addPiece(String id, String imagePath, String dropZoneID, double size) {
        Piece piece = new Piece(id,this, imagePath, size);
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
        gameObjects.put(id,piece);
        pieceToDropZoneMap.put(id, dropZoneID);
        root.getChildren().add(piece.getNode());
    }

    @Override
    public void setClickable(List<String> ids) {
        clearClickables();
        clickable.addAll(ids);
        for (String id : ids){
            gameObjects.get(id).makePlayable();
            //AbstractSelectableVisual gameObjectVisual = (AbstractSelectableVisual) gameObjects.get(id).getNode();
            //gameObjectVisual.showClickable();
        }
    }

    @Override
    public void movePiece(String pieceID, String dropZoneID) {
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        //pieces.get(pieceID).moveToDropZoneXY(dropZone.getDropZoneCenter());
        pieceToDropZoneMap.put(pieceID, dropZoneID);
    }

    @Override
    public void removePiece(String pieceID) {
        pieceToDropZoneMap.remove(pieceID);
        root.getChildren().remove(gameObjects.get(pieceID).getNode());
        gameObjects.remove(gameObjects.get(pieceID));
    }

    @Override
    public void setObjectImage(String id, String imagePath) {
        /*
        Image img;
        try {
            img = new Image(new FileInputStream(imagePath));
        } catch (Exception e) {
            System.out.println("Image " + imagePath + " not found");
            return;
        }
        ImageView imgv = new ImageView(img);
        ImageView oldimg = (ImageView) ((HBox) gameObjects.get(id).getNode()).getChildren().get(0);
        int size = (int) oldimg.getFitWidth();

        imgv.setFitWidth(size);
        imgv.setFitHeight(size);
        AbstractSelectableVisual gameObjectVisual = (AbstractSelectableVisual) gameObjects.get(id).getNode();
        gameObjectVisual.updateVisual(imgv);
         */
        System.out.println("updating Visual");
        System.out.println(imagePath);
        GameRunnerObject gameObject = gameObjects.get(id);
        gameObject.setImage(imagePath);
        SelectableVisual selectableVisual = (SelectableVisual) gameObject.getNode();
        root.getChildren().remove(selectableVisual);
        selectableVisual.updateVisual(gameObject.getImage());
        root.getChildren().add((Node) selectableVisual);
    }

    private void clearClickables(){
        for (String id : clickable){
            gameObjects.get(id).makeUnplayable();
            //AbstractSelectableVisual gameObjectVisual = (AbstractSelectableVisual) gameObjects.get(id).getNode();
            //gameObjectVisual.showUnclickable();
        }
        clickable.clear();
    }
    @Override
    public boolean isObjectPlayable(String id){
        return gameObjects.get(id).getPlayable();
    }

    public ArrayList<Node> getNodes(){
        ArrayList<Node> nodelist = new ArrayList<>();
        for (GameRunnerObject gameObject : gameObjects.values()) {
            nodelist.add(gameObject.getNode());
        }
        return nodelist;
    }
}

