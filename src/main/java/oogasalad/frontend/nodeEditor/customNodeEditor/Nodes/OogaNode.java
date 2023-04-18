package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
//import oogasalad.frontend.nodeEditor.customNodeEditor.Utils.Ports.Port;

import java.util.List;
import javafx.scene.layout.VBox;
//import oogasalad.frontend.nodeEditor.customNodeEditor.Utils.Connections.Connection;
//import oogasalad.frontend.nodeEditor.customNodeEditor.Utils.Ports.Port;

import java.util.List;

public abstract class OogaNode extends VBox{

    protected double x, y;
    protected double xOffset, yOffset;

    protected String color;

//    protected List<Port> ports;

    public OogaNode(double x, double y, double width, double height, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(width, height);
        setColor(color);
        setContent();
//    this.setStyle("-fx-border-color: black");

        this.setOnMousePressed(event -> {
            if (event.isShiftDown()) {
                this.delete();
            }

        });

    }

    protected abstract void setContent();

    public abstract String sendContent();

    protected void delete() {
        Pane obj = (Pane) this.getParent();
        System.out.println(obj.getChildren());
        obj.getChildren().remove(this);
    }

    protected void onMousePressed(){
        this.setOnMousePressed(event -> {
            System.out.println("pressed");
            if (event.isShiftDown()) {
                System.out.println("pressed + down");
                this.delete();
            }
        });
    }

    protected void setColor(String color) {
        setStyle("-fx-background-color: " + color);
    }

    protected void setX(double x) {
        this.x = x;
    }

    protected void setY(double y) {
        this.y = y;
    }

    protected double getX() {
        return this.x;
    }

    protected double getY() {
        return this.y;
    }

    protected String getColor() {
        return this.color;
    }


}
