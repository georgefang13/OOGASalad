package oogasalad.Controller;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneFE;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.GameRunnerObject;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Piece;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.SelectableVisual;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        try {
            game = new Game(this,directory,numPlayers,false);
            loadGame(directory);
            game.startGame();
        } catch (FileNotFoundException e){
            System.out.println("FAIL"); //TODO: error handle
        }
    }
    private void loadGame(String directory) throws FileNotFoundException {
        directory = directory + "/frontend";
        loadDropZones(directory);
        loadPieces(directory);
    }
    private Node loadImage(String imgPath,int height, int width){ //TODO: A similar function is in game object move it there just send paths
        Image img;
        try {
            img = new Image(new FileInputStream(imgPath));
        } catch (Exception e) {
            System.out.println("Image " + imgPath + " not found");
            return null;
        }
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        return imageView;
    }
    private Node loadDefaultDropRectangle(String color,int height, int width){ //TODO: A similar function is in game object move it there just send paths
        Color fillColor = Color.valueOf(color);
        Rectangle defaultDrop = new Rectangle(width, height, fillColor);
        return defaultDrop;
    }

    private void loadDropZones(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/layout.json");
        for (String id : fm.getTagsAtLevel()){
            int x = Integer.parseInt(fm.getString(id, "x"));
            int y = Integer.parseInt(fm.getString(id, "y"));
            int height = Integer.parseInt(fm.getString(id, "height"));
            int width = Integer.parseInt(fm.getString(id, "width"));

            boolean selectedisimage = fm.getObject(Boolean.class, id, "selected", "hasImage");
            String selectedparam = fm.getString(id, "selected", "param");
            Node selected;
            if (selectedisimage){
                String selectedPath = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + selectedparam;
                selected = loadImage(selectedPath,height,width);
            } else {
                selected = loadDefaultDropRectangle(selectedparam,height,width);
            }

            boolean unselectedisimage = fm.getObject(Boolean.class, id, "unselected", "hasImage");
            String unselectedparam = fm.getString(id, "unselected", "param");
            Node unselected;
            if (unselectedisimage){
                String selectedPath = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + unselectedparam;
                unselected = loadImage(selectedPath,height,width);
            } else {
                unselected = loadDefaultDropRectangle(selectedparam,height,width);
            }

            addDropZone(new GameController.DropZoneParameters(id, unselected, selected, x, y, height, width));
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
    @Override
    public void select(String id) {
        if (clickable.contains(id)) {
            game.clickPiece(id);
        }
    }

    @Override
    public void addDropZone(GameController.DropZoneParameters params) {
        DropZoneFE dropZone = new DropZoneFE(params.id(), params.unselected(), params.selected(), params.width(), params.height(), params.x(),params.y(),this);
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
            System.out.println(id);
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

