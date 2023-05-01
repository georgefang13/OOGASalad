package oogasalad.frontend.components.gridObjectComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.AbstractComponent;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

import java.util.Map;

public class GridObject extends AbstractComponent {
    private String name;
    private int rows;
    private int cols;
    private Set<String> content;
    private List<Dropzone> dropzones;
    private GridPane grid;
    private double width;
    private double height;
    private double rotate;
    private double rectWidth;
    private double rectHeight;
    private final static String BOARD_TYPE = "createGrid";
    public GridObject(String ID, Map<String, String> map){
        super(ID);
        setValuesfromMap(map);
        initialize();
        followMouse();
    }

    private void initialize() {
        content = new HashSet<>();
        dropzones = new ArrayList<>();
        grid = new GridPane();
        grid.setPrefWidth(width);
        grid.setPrefHeight(height);
        grid.setPickOnBounds(false);
        rectWidth = width/cols;
        rectHeight = height/rows;
        ComponentsFactory factory = new ComponentsFactory();
        Map<String, String> params = new HashMap<>();
        params.put("width", Double.toString(rectWidth));
        params.put("height", Double.toString(rectHeight));
        params.put("fill", "White");
        params.put("border", "Black");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Dropzone d = (Dropzone) factory.create("Dropzone", params);
                grid.getChildren().add(d.getNode());
                d.getNode().setLayoutX(rectWidth*j);
                d.getNode().setLayoutY(rectHeight*i);
                content.add(d.getID());
                dropzones.add(d);
            }
        }
    }



    @Override
    public Node getNode(){
        return grid;
    }

    public GridPane getGrid(){
        return grid;
    }

    public Set<String> getContent() {
        return content;
    }

    public double getRectHeight() {
        return rectHeight;
    }

    public double getRectWidth() {
        return rectWidth;
    }

    public List<Dropzone> getDropzones() {
        return dropzones;
    }
}
