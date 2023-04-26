package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.Controller.GameRunnerController;

public class DropZoneFE implements FloatingDropHolder{
    VBox dropZone;
    String dropID;
    GameRunnerController gameRunnerController;
    public DropZoneFE(String id, int width, int height, int x, int y, GameRunnerController gameRunnerController) {
        dropID = id;
        Rectangle fill = new Rectangle(width, height);
        fill.setStroke(Color.BLACK);
        fill.setFill(Color.SKYBLUE);
        dropZone = new VBox(fill);
        dropZone.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        dropZone.setPrefWidth(width);
        dropZone.setPrefHeight(height);
        dropZone.setLayoutX(x);
        dropZone.setLayoutY(y);
        dropZone.toBack();
        this.gameRunnerController = gameRunnerController;
        droppable();
    }

    @Override
    public VBox getVisual() {
        return dropZone;
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
        gameRunnerController.dropZoneSelected(dropID);
    }

}
