package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class DropZoneVisual extends HBox {
    private Node unselectedVisual;
    private Node selectedVisual;
    private String dropID;
    public DropZoneVisual(Node dropZoneImage,Node selectedDropZoneImage, double width, double height, double x, double y,String id) {
        super(dropZoneImage);
        unselectedVisual = dropZoneImage;
        selectedVisual = selectedDropZoneImage;
        dropID = id;
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.toBack();
    }
    public void selected(){
        this.getChildren().remove(unselectedVisual);
        this.getChildren().add(selectedVisual);
    }
    public void deselected(){
        this.getChildren().remove(selectedVisual);
        this.getChildren().add(unselectedVisual);
    }
    public String getDropID(){
        return dropID;
    }
}
