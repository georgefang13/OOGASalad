package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import oogasalad.Controller.GameRunnerController;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Piece extends GameRunnerObject{
    private double lastTranslateX;
    private double lastTranslateY;
    public Piece(String ID, GameRunnerController gameRunnerController, String imagepath, double size) {
        super(ID, gameRunnerController, imagepath, size);
    }
    public void moveToDropZoneXY(double x, double y){
        getNode().setLayoutX(x);
        getNode().setLayoutY(y);
    }
    public Node getPieceBox(){
        Node pieceNode = getNode();
        VBox pieceBox = new VBox(pieceNode);
        return pieceBox;
    }
    @Override
    public void onDragDropped() {
        gameRunnerController.updatePieceMove(ID);
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
