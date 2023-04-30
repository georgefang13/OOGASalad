package oogasalad.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.*;
import oogasalad.frontend.managers.DisplayManager;
import oogasalad.gamerunner.backend.Game;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileNotFoundException;
import java.util.*;

public class GameRunnerController implements GameController {
    private final Map<String, GameRunnerObject> gameObjects = new HashMap<>();
    private final ObservableList<Node> gameObjectVisualsList = FXCollections.observableArrayList();
    private final Map<String, String> pieceToDropZoneMap = new HashMap<>();
    private final HashSet<String> clickable = new HashSet<>();
    private Game game;

    public GameRunnerController(String gameName) {
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
    public void assignUndoButtonAction(Button undoButton){
        undoButton.setOnAction(e -> game.undoClickPiece());
    }
    private void loadGame(String directory) throws FileNotFoundException {
        directory = directory + "/frontend";
        loadDropZones(directory);
        loadPieces(directory);
    }

    private Node loadDefaultDropRectangle(String hexColor,int height, int width){ //TODO: A similar function is in game object move it there just send paths
        Color fillColor = Color.web(hexColor);
        Rectangle defaultDrop = new Rectangle(width, height, fillColor);
        return defaultDrop;
    }
    private Node loadImgOrDefaultFromFile(String selectType, FileManager fm, String id, String directory, int height, int width){
        boolean isImage = fm.getObject(Boolean.class, id, selectType, "hasImage");
        String param = fm.getString(id, selectType, "param");
        Node visual;
        if (isImage){
            String selectedPath = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + param;
            visual = DisplayManager.loadImage(selectedPath,height,width);
        } else {
            visual = loadDefaultDropRectangle(param,height,width);
        }
        return visual;
    }

    private void loadDropZones(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/layout.json");
        for (String id : fm.getTagsAtLevel()){
            int x = Integer.parseInt(fm.getString(id, "x"));
            int y = Integer.parseInt(fm.getString(id, "y"));
            int height = Integer.parseInt(fm.getString(id, "height"));
            int width = Integer.parseInt(fm.getString(id, "width"));

            Node unselected = loadImgOrDefaultFromFile("unselected",fm,id,directory,height,width);
            Node selected = loadImgOrDefaultFromFile("selected",fm,id,directory,height,width);
            addDropZone(new GameController.DropZoneParameters(id, unselected, selected, x, y, height, width));
        }
    }

    private void loadPieces(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/objects.json");
        for (String id : fm.getTagsAtLevel()){
            String image = fm.getString(id, "defaultImage");
            image = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + image;
            String dropZoneID = fm.getString(id, "location");
            double height = Double.parseDouble(fm.getString(id, "height"));
            double width = Double.parseDouble(fm.getString(id, "width"));

            boolean hasimage = fm.getObject(Boolean.class,id,"selected","hasSelectedImage");
            String paramString = fm.getString(id,"selected","param");

            Object param;
            if (hasimage){
                param = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + paramString;
            } else {
                param = Color.web(paramString);
            }
            addPiece(id, image, dropZoneID, hasimage, param, height, width);
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
        addGameObject(params.id(),dropZone);
    }

    @Override
    public void addPiece(String id, String imagePath, String dropZoneID, boolean hasSelectImage, Object param, double height, double width) {
        Piece piece = new Piece(id,this, imagePath, hasSelectImage, param ,height, width);
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
        pieceToDropZoneMap.put(id, dropZoneID);
        addGameObject(id,piece);
    }
    private void addGameObject(String id, GameRunnerObject gameObject){
        gameObjects.put(id,gameObject);
        gameObjectVisualsList.add(gameObject.getNode());
    }
    private void removeGameObject(String id){
        gameObjectVisualsList.remove(gameObjects.get(id).getNode());
        gameObjects.remove(id);
    }

    @Override
    public void setClickable(List<String> ids) {
        clearClickables();
        clickable.addAll(ids);
        for (String id : ids){
            gameObjects.get(id).makePlayable();
        }
    }

    @Override
    public void movePiece(String pieceID, String dropZoneID) {
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        Piece piece = (Piece) gameObjects.get(pieceID);
        piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
        pieceToDropZoneMap.put(pieceID, dropZoneID);
    }

    @Override
    public void removePiece(String pieceID) {
        pieceToDropZoneMap.remove(pieceID);
        removeGameObject(pieceID);
    }

    @Override
    public void setObjectImage(String id, String imagePath) {
        GameRunnerObject gameObject = gameObjects.get(id);
        gameObject.setImage(imagePath);
        SelectableVisual selectableVisual = (SelectableVisual) gameObject.getNode();
        selectableVisual.updateVisual(gameObject.getImage());
    }

    private void clearClickables(){
        for (String id : clickable){
            gameObjects.get(id).makeUnplayable();
        }
        clickable.clear();
    }
    @Override
    public boolean isObjectPlayable(String id){
        return gameObjects.get(id).getPlayable();
    }

    @Override
    public ObservableList<Node> getGameObjectVisuals(){
        Comparator<Node> nodeComparator = new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if (node1 instanceof DropZoneVisual && node2 instanceof PieceVisual) {
                    return -1;
                } else if (node2 instanceof DropZoneVisual && node1 instanceof PieceVisual) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(gameObjectVisualsList, nodeComparator);
        return gameObjectVisualsList;
    }
}

