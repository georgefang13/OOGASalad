package oogasalad.Controller;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.*;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
import oogasalad.frontend.managers.GameObjectVisualSorter;
import oogasalad.gamerunner.backend.Game;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Owen MacKenzie
 * @author Ethan Horowitz
 */

public class GameRunnerController implements GameController {
    private final Map<String, GameRunnerComponent> gameObjects = new HashMap<>();
    private final ObservableList<Node> gameObjectVisualsList = FXCollections.observableArrayList();
    private final HashSet<String> clickable = new HashSet<>();
    private Game game;
    private ObjectProperty<Boolean> endGame;

    public GameRunnerController(String gameName, ArrayList<String> gameTypeData) {
        String directory = "data/games/"+gameName;
        int numPlayers = 2;
        String type = gameTypeData.get(0);
        endGame = new SimpleObjectProperty<>(false);

        try {
            loadGame(directory);

            game = new Game(this, directory, numPlayers,  !type.equals("local"));

            switch (type) {
                case "local" -> game.startGame();
                case "create" -> game.createOnlineGame();
                case "join" -> {
                    String code = gameTypeData.get(1);
                    game.joinOnlineGame(code);
                }
                default -> System.out.println("didn't find that option");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void assignUndoButtonAction(Button undoButton){
        undoButton.setOnAction(e -> game.undoClickPiece());
    }


    /**
     *
     * @param directory
     * @throws FileNotFoundException
     */
    private void loadGame(String directory) throws FileNotFoundException {
        directory = directory + "/frontend";
        loadDropZones(directory);
        loadPieces(directory);
    }

    private AbstractSelectableVisual.SelectableVisualParams loadDropParamsFromFile(String selectType, FileManager fm, String id, String directory){
        boolean isImage = fm.getObject(Boolean.class, id, selectType, "hasImage");
        String param = fm.getString(id, selectType, "param");
        if (isImage){
            param = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + param;
        }
        return new AbstractSelectableVisual.SelectableVisualParams(isImage,param);
    }
    private void loadDropZones(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/layout.json");
        for (String id : fm.getTagsAtLevel()){
            int x = Integer.parseInt(fm.getString(id, "x"));
            int y = Integer.parseInt(fm.getString(id, "y"));
            int height = Integer.parseInt(fm.getString(id, "height"));
            int width = Integer.parseInt(fm.getString(id, "width"));

            AbstractSelectableVisual.SelectableVisualParams unselected = loadDropParamsFromFile("unselected",fm,id,directory);
            AbstractSelectableVisual.SelectableVisualParams selected = loadDropParamsFromFile("selected",fm,id,directory);
            addDropZone(new GameController.DropZoneParameters(id, unselected, selected, x, y, height, width));
        }
    }

    private void loadPieces(String directory) throws FileNotFoundException {
        FileManager fm = new FileManager(directory + "/objects.json");
        for (String id : fm.getTagsAtLevel()){
            String image = fm.getString(id, "defaultImage");
            image = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + image;
            String dropZoneID = fm.getString(id, "location");
            int height = (int) Double.parseDouble(fm.getString(id, "height"));
            int width = (int) Double.parseDouble(fm.getString(id, "width"));

            boolean hasimage = fm.getObject(Boolean.class,id,"selected","hasSelectedImage");
            String paramString = fm.getString(id,"selected","param");

            if (hasimage){
                paramString = directory.substring(0, directory.lastIndexOf("/")) + "/assets/" + paramString;
            }
            addPiece(id, image, dropZoneID, hasimage, paramString, height, width);
        }
    }
    @Override
    public void select(String id) {
        System.out.println(id);
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
    public void addPiece(String id, String imagePath, String dropZoneID, boolean hasSelectImage, String param, int height, int width) {
            Piece piece = new Piece(id,this, imagePath, hasSelectImage, param ,height, width);
            DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
            piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
            addGameObject(id,piece);
    }
    private void addGameObject(String id, GameRunnerComponent gameObject){
            gameObjects.put(id,gameObject);
            gameObjectVisualsList.add(gameObject.getNode());
    }
    private void removeGameObject(String id){
        gameObjectVisualsList.remove(gameObjects.get(id).getNode());
        gameObjects.remove(id);
        /*
        Platform.runLater(() -> {

        });

         */
    }

    @Override
    public void setClickable(List<String> ids) {
        clearClickables();
        clickable.addAll(ids);
        for (String id : ids){
            gameObjects.get(id).makePlayable();
        }
        /*
        Platform.runLater(() -> {

        });

         */
    }

    @Override
    public void movePiece(String pieceID, String dropZoneID) {
            DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
            Piece piece = (Piece) gameObjects.get(pieceID);
            piece.moveToDropZoneXY(dropZone.getDropZoneCenter());
    }

    @Override
    public void removePiece(String pieceID) {
            removeGameObject(pieceID);
    }

    @Override
    public void setObjectImage(String id, String newImagePath) {
        GameRunnerComponent gameObject = gameObjects.get(id);
        AbstractSelectableVisual.SelectableVisualParams unselected = new AbstractSelectableVisual.SelectableVisualParams(true,newImagePath);
        AbstractSelectableVisual.SelectableVisualParams selected = new AbstractSelectableVisual.SelectableVisualParams(false,"#ff0000");

        Node oldObjectVisual = gameObject.getNode();
        gameObject.setSelectableVisual(unselected, selected);
        updateVisualDisplay(gameObject, oldObjectVisual);
    }

    private void updateVisualDisplay(GameRunnerComponent gameObject, Node oldObjectVisual) {
        Node newObjectVisual = gameObject.getNode();
        newObjectVisual.setTranslateX(oldObjectVisual.getTranslateX());
        newObjectVisual.setTranslateY(oldObjectVisual.getTranslateY());
        gameObjectVisualsList.remove(oldObjectVisual);
        gameObjectVisualsList.add(newObjectVisual);
    }

    @Override
    public void setPieceHighlight(String id, String param) {
        GameRunnerComponent gameObject = gameObjects.get(id);
        boolean isImg = !param.contains("#");
        AbstractSelectableVisual.SelectableVisualParams selected = new AbstractSelectableVisual.SelectableVisualParams(isImg,param);

        Node oldObjectVisual = gameObject.getNode();
        gameObject.setSelectVisual(selected);
        updateVisualDisplay(gameObject, oldObjectVisual);
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
        GameObjectVisualSorter gameObjectVisualComparator = new GameObjectVisualSorter();
        Collections.sort(gameObjectVisualsList, gameObjectVisualComparator);
        return gameObjectVisualsList;
    }
    @Override
    public ObjectProperty<Boolean> getEndGameStatus(){
        return endGame;
    }

    @Override
    public void passGameId(String code) {
        Platform.runLater(() -> {
//            gameCode.setText(code);
        });
    }

    @Override
    public void addTextObject(String id, String text, String dropZoneID) {
        TextGameRunner textGameRunner = new TextGameRunner(id);
        textGameRunner.setText(text);
        DropZoneFE dropZone = (DropZoneFE) gameObjects.get(dropZoneID);
        textGameRunner.moveToXY(dropZone.getDropZoneCenter());
        addGameObject(id,textGameRunner);
    }

    @Override
    public void updateTextObject(String id, String text) {

    }
}

