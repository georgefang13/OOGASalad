package oogasalad.frontend.nodeEditor.myNodeEditor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DraggableItem extends VBox {

    private double x, y;
    private String color;
    private ObservableList<Node> children;

    public DraggableItem(double x, double y, double width, double height, String color, ObservableList<Node> children) {
        this.x = x;
        this.y = y;
        this.color = color;
        setChildren(children);
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(width, height);
        setColor(color);
    }

    public DraggableItem(double x, double y, double width, double height, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(width, height);
        setColor(color);
        setContent();
    }

    public DraggableItem(DraggableItem copy){
        this.x = copy.getX();
        this.y = copy.getY();
        this.color = copy.getColor();
        setLayoutX(x);
        setLayoutY(y);
        setPrefSize(copy.getMyWidth(),copy.getMyHeight());
        setColor(color);
        setChildren(copy.getChildren());
    }

    private void setChildren(ObservableList<Node> children) {
        this.getChildren().addAll(children);
    }

    public void setColor(String color) {
        setStyle("-fx-background-color: " + color);
    }

    private void setContent() {

        MyTextField textField = new MyTextField("Hello World");
        getChildren().add(textField);

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getMyWidth() {
        return this.getPrefWidth();
    }

    public double getMyHeight() {
        return this.getPrefHeight();
    }

    public String getColor() {
        return this.color;
    }
}
