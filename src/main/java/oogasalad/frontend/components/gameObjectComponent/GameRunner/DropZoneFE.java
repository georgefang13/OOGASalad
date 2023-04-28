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
    private int x;
    private int y;
    private int height;
    private int width;
    public DropZoneFE(String id, int width, int height, int x, int y, GameController gameRunnerController) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

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
        dropStack.setOnMouseReleased(e -> onDrop());

        this.gameRunnerController = gameRunnerController;
        //droppable();
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

    public void droppable(){
        dropZone.setOnMouseReleased( e -> {
            onDrop();
        });
    }
    public void onDrop(){
        gameRunnerController.select(dropID);
    }

    record dropZoneCenter(double x, double y){};

    private double toCenter(int start,int length){
        return start + ((double) length)/2;
    }

    public dropZoneCenter getDropZoneCenter(){
        double centerX = toCenter(x,width);
        double centerY = toCenter(y,height);
        return new dropZoneCenter(centerX,centerY);
    }

}
