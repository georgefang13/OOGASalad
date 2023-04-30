package oogasalad.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.*;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneFE;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.GameRunnerObject;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Piece;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.SelectableVisual;
import oogasalad.frontend.managers.DisplayManager;
import oogasalad.gamerunner.backend.Game;
import oogasalad.gamerunner.backend.GameController;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class GameRunnerController implements GameController {
    private final Map<String, GameRunnerObject> gameObjects = new HashMap<>();
    private final Map<String, String> pieceToDropZoneMap = new HashMap<>();
    private final HashSet<String> clickable = new HashSet<>();
    private Game game;
    private BorderPane root;

    public GameRunnerController(String gameName, BorderPane root) {
        String directory = "data/games/"+gameName;
        this.root = root;
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
        //root.getChildren().add(dropZone.getNode());
    }

    @Override
    public void addPiece(String id, String imagePath, String dropZoneID, boolean hasSelectImage, Object param, double height, double width) {
        Piece piece = new Piece(id,this, imagePath, hasSelectImage, param ,height, width);
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
        gameObjects.put(id,piece);
        pieceToDropZoneMap.put(id, dropZoneID);
        //root.getChildren().add(piece.getNode());
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
        Piece piece = (Piece) gameObjects.get(pieceID);
        piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
        pieceToDropZoneMap.put(pieceID, dropZoneID);
    }

    @Override
    public void removePiece(String pieceID) {
        pieceToDropZoneMap.remove(pieceID);
        //root.getChildren().remove(gameObjects.get(pieceID).getNode());
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
        //root.getChildren().remove(selectableVisual);
        selectableVisual.updateVisual(gameObject.getImage());
        //root.getChildren().add((Node) selectableVisual);
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

    public ObservableList<Node> getGameObjectVisuals(){
        ObservableList<Node> ObjectVisualList = FXCollections.observableArrayList();
        for (GameRunnerObject gameObject : gameObjects.values()) {
            ObjectVisualList.add(gameObject.getNode());
        }
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
        Collections.sort(ObjectVisualList, nodeComparator);
        return ObjectVisualList;
    }
}

