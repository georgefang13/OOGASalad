package oogasalad.frontend.components.gridObjectComponent;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.DropzoneComponent.Dropzone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

import java.util.Map;

public class GridObject extends AbstractComponent {
    private String name;
    private int rows;
    private int cols;
    private Dropzone[][] nodes;
    private GridPane grid;
    private double width;
    private double height;


    public GridObject(String ID, Map<String, String> map){
        super(ID);
        setValuesfromMap(map);
        initialize();
    }

    private void initialize() {
        DropZone[][] nodes = new DropZone[rows][cols];
        grid = new GridPane();
        grid.setPrefWidth(width);
        grid.setPrefHeight(height);
        double rectWidth = width/rows;
        double rectHeight = height/cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle rect = new Rectangle(rectWidth, rectHeight);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                grid.add(rect,i,j);
            }
        }

        followMouse();
    }

    @Override
    public Node getNode(){
        return grid;
    }


}
