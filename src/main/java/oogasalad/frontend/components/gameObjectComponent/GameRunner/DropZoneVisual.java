package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class DropZoneVisual extends AbstractSelectableVisual {
    private Node unselectedVisual;
    private Node selectedVisual;
    public DropZoneVisual(Node dropZoneImage,Node selectedDropZoneImage, double width, double height, double x, double y,String id) {
        super(id);
        unselectedVisual = dropZoneImage;
        selectedVisual = selectedDropZoneImage;
        initHBox(width,height,x,y);
        this.getChildren().add(unselectedVisual);
    }
    private void initHBox(double width, double height, double x, double y){
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.toBack();
    }

    @Override
    public void showClickable() {
        this.getChildren().add(selectedVisual);
        this.getChildren().remove(unselectedVisual);
    }

    @Override
    public void showUnclickable() {
        this.getChildren().remove(selectedVisual);
        this.getChildren().add(unselectedVisual);
    }
}
