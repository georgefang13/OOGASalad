package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.Controller.GameRunnerController;
import oogasalad.gamerunner.backend.GameController;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Piece extends GameRunnerObject{
    private double lastTranslateX;
    private double lastTranslateY;
    private HBox pieceBox;
    public Piece(String ID, GameController gameRunnerController, String imagepath, double size) {
        super(ID, gameRunnerController, imagepath, size);
        initializePieceBox();
    }

    private void initializePieceBox() {
        Node pieceNode = getNode();
        pieceBox = new HBox(pieceNode);
        pieceBox.setPrefHeight(size);
        pieceBox.setPrefWidth(size);
        pieceBox.setMaxHeight(size);
        pieceBox.setMaxHeight(size);
        pieceBox.setOnMouseClicked(e -> gameRunnerController.select(ID));
    }

    public void moveToDropZoneXY(DropZoneFE.dropZoneCenter DZcenter){
        setCentertoCenter(DZcenter.x(), DZcenter.y());
    }
    private void setCentertoCenter(double x, double y){
        pieceBox.setLayoutX(x-size/2);
        pieceBox.setLayoutY(y-size/2);
    }
    public Node getPieceBox(){
        return pieceBox;
    }
    @Override
    public void onDragDropped() {
        gameRunnerController.select(ID);
    }

    @Override
    public void onClick() {
        gameRunnerController.select(ID);
    }

    @Override
    public void acceptDrag() {
        Node node = getNode();
        lastTranslateX = node.getTranslateX();
        lastTranslateY = node.getTranslateY();
    }

    @Override
    public void goBack() {
        Node node = getNode();
        node.setTranslateX(lastTranslateX);
        node.setTranslateY(lastTranslateY);
    }
}
