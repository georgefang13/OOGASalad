package oogasalad.frontend.components.rectangleObjectComponent;
import javafx.scene.Node;
import oogasalad.frontend.components.AbstractComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Map;
import java.util.ResourceBundle;


public class RectangleObject extends AbstractComponent implements RectangleObjectComponent {
    private String name;
    private Rectangle rectangle;
    private double width;
    private double height;
    private Color color;
    private double rotate;

    public RectangleObject(String ID){
        super(ID);
        instantiatePropFile("frontend.properties.Defaults.RectangleObject");
        //this.setDefault();
        this.followMouse();
        this.getNode();
    }
    public RectangleObject(String ID, Map<String, String> map){
        super(ID);
        setDraggable(true);
        setValuesfromMap(map);
        initialize();
        followMouse();
    }

    private void initialize() {
        rectangle = new Rectangle(width,height);
        rectangle.setFill(color);
        rectangle.setRotate(rotate);
    }

    @Override
    public Shape getNode() {
        return rectangle;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
        rectangle.setWidth(width);
    }

    @Override
    public double getWidth(double width) {
        return rectangle.getWidth();
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
        rectangle.setHeight(height);
    }

    @Override
    public double getHeight(double height) {
        return rectangle.getHeight();
    }

    @Override
    public void setFill(Color fill) {
        this.color = color;
        rectangle.setFill(fill);
    }

    @Override
    public Color getFill() {
        return (Color)rectangle.getFill();
    }
}