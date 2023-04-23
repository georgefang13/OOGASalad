package oogasalad.frontend.components.rectangleObjectComponent;
import javafx.scene.Node;
import oogasalad.frontend.components.AbstractComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ResourceBundle;


public class RectangleObject extends AbstractComponent implements RectangleObjectComponent {
    private double x;
    private double y;
    private double width;
    private double height;
    private Color fill;
    private double strokeWidth;
    private Color strokeColor;
    private Rectangle rectangle;
    private final String DEFAULT_FILE_PATH = "frontend.properties.Defaults.RectangleObject";
    private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

    public RectangleObject(int ID){
        super(ID);
        this.setDefault();
        this.followMouse();
        this.getNode();
    }

    public RectangleObject(int ID, Node container){
        super(ID, container);
        this.setDefault();
    }

    public RectangleObject(int ID, Node container, double x, double y, double width, double height) {
        super(ID, container);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle(x, y, width, height);
    }

    @Override
    public void setFill(Color fill) {
        this.fill = fill;
        rectangle.setFill(fill);
    }

    @Override
    public Color getFill() {
        return fill;
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        rectangle.setStrokeWidth(strokeWidth);
    }

    @Override
    public double getStrokeWidth() {
        return strokeWidth;
    }

    @Override
    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        rectangle.setStroke(strokeColor);

    }

    @Override
    public Color getStrokeColor() {
        return strokeColor;
    }

    @Override
    public Shape getNode() {
        return rectangle;
    }

    @Override
    public void setDefault() {
        x = Double.parseDouble(DEFAULT_BUNDLE.getString("rectangle.x"));
        y = Double.parseDouble(DEFAULT_BUNDLE.getString("rectangle.y"));
        width = Double.parseDouble(DEFAULT_BUNDLE.getString("rectangle.width"));
        height = Double.parseDouble(DEFAULT_BUNDLE.getString("rectangle.height"));
        this.rectangle = new Rectangle(x, y, width, height);
    }
}