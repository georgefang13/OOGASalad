package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.Controller.GameRunnerController2;
import oogasalad.gamerunner.backend.GameController;

import javafx.event.Event;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.MouseEvent.MOUSE_RELEASED;

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
        //pieceBox.setAlignment(Pos.CENTER);
        pieceBox.setPrefHeight(size);
        pieceBox.setPrefWidth(size);
        pieceBox.setMaxHeight(size);
        pieceBox.setMaxHeight(size);
        pieceBox.setOnDragDetected(e -> gameRunnerController.select(ID));
        pieceBox.setOnMouseReleased(e -> selectDropZoneBelow());
    }
    private void selectDropZoneBelow(){
        DropZoneVisual dzv = getIntersectingDropZones();
        gameRunnerController.select(dzv.getDropID());
    }
    public DropZoneVisual getIntersectingDropZones(){
        Bounds pieceBounds = pieceBox.localToScene(pieceBox.getBoundsInLocal());
        for (Node n : pieceBox.getParent().getChildrenUnmodifiable()) {
            if (n instanceof DropZoneVisual){
                if (n.localToScene(n.getBoundsInLocal()).intersects(pieceBounds)){
                    return (DropZoneVisual) n;
                }
            }
        }
        //goBack();
        return null;
    }

    public void moveToDropZoneXY(DropZoneFE.dropZoneCenter DZcenter){
        setCentertoCenter(DZcenter.x(), DZcenter.y());
        //acceptDrag();
    }
    private void setCentertoCenter(double x, double y){
        //double actualX = pieceBox.getLayoutX() + pieceBox.getTranslateX();
        //double actualY = pieceBox.getLayoutY() + pieceBox.getTranslateY();
        double shiftX = x - size/2;
        double shiftY = y - size/2;
        //this.resetOffset();
        pieceBox.setTranslateX(shiftX);
        pieceBox.setTranslateY(shiftY);
    }
    public Node getPieceBox(){
        return pieceBox;
    }
    @Override
    public void onDragDropped() {
    }

    @Override
    public void onClick() {
        //gameRunnerController.select(ID);
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
