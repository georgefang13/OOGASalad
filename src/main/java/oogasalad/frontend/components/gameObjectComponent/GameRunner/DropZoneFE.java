package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.Controller.GameRunnerController;
import oogasalad.gamerunner.backend.GameController;

public class DropZoneFE implements FloatingDropHolder{
    HBox dropZone;
    StackPane dropStack;
    String dropID;
    GameController gameRunnerController;
    public DropZoneFE(String id, int width, int height, int x, int y, GameController gameRunnerController) {
        dropID = id;
        Rectangle fill = new Rectangle(width, height);
        fill.setStroke(Color.BLACK);
        fill.setFill(Color.SKYBLUE);
        dropStack = new StackPane(fill);
        dropZone = new HBox(dropStack);
        dropZone.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        dropZone.setPrefWidth(width);
        dropZone.setPrefHeight(height);
        dropZone.setLayoutX(x);
        dropZone.setLayoutY(y);
        dropZone.toBack();
        dropStack.setOnMouseClicked(e -> gameRunnerController.select(dropID));

        this.gameRunnerController = gameRunnerController;
        droppable();
    }

    @Override
    public HBox getVisual() {
        return dropZone;
    }

    public StackPane getDropStack(){
        return dropStack;
    }
    public void addPieceToDropZone(Node piece){
        dropStack.getChildren().add(piece);
    }
    public void removePieceFromDropZone(Node piece){
        dropStack.getChildren().remove(piece);
    }

    @Override
    public String getDropZoneID() {
        return dropID;
    }

    void droppable(){
        dropZone.setOnMouseReleased( e -> {
        });
    }
    void onDrop(){
        gameRunnerController.select(dropID);
    }

}
