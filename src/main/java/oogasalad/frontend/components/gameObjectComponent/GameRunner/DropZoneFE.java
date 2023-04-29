package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.gamerunner.backend.GameController;

public class DropZoneFE implements FloatingDropHolder{
    DropZoneVisual dropZoneVisual;
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

        Rectangle unselectedfill = new Rectangle(width, height);
        unselectedfill.setStroke(Color.BLACK);
        unselectedfill.setFill(Color.SKYBLUE);

        Rectangle selectedfill = new Rectangle(width, height);
        selectedfill.setStroke(Color.BLACK);
        selectedfill.setFill(Color.YELLOW);

        dropZoneVisual = new DropZoneVisual(unselectedfill,selectedfill,width,height,x,y,dropID);
        dropZoneVisual.setOnMouseClicked(e -> gameRunnerController.select(dropID));
        dropZoneVisual.setOnMouseReleased(e -> onDrop());

        this.gameRunnerController = gameRunnerController;
        //droppable();
    }

    @Override
    public HBox getVisual() {
        return dropZoneVisual;
    }
    /*
    public void addPieceToDropZone(Node piece){
        dropStack.getChildren().add(piece);
    }
    public void removePieceFromDropZone(Node piece){
        dropStack.getChildren().remove(piece);
    }

     */

    @Override
    public String getDropZoneID() {
        return dropID;
    }
/*
    public void droppable(){
        dropZone.setOnMouseReleased( e -> {
            onDrop();
        });
    }

 */
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
