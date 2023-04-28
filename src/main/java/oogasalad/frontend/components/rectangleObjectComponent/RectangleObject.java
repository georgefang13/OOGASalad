package oogasalad.frontend.components.rectangleObjectComponent;
import javafx.scene.Node;
import oogasalad.frontend.components.AbstractComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Map;
import java.util.ResourceBundle;


public class RectangleObject extends AbstractComponent implements RectangleObjectComponent {
    private Rectangle rectangle;
    double x;
    double y;
    double width;
    double height;

    public RectangleObject(int ID, Map<String, String> map){
        super(ID);
        setValuesfromMap(map);
        rectangle = new Rectangle(x,y,width,height);
        this.followMouse();

    }

    @Override
    public void setFill(Color fill) {
        rectangle.setFill(fill);
    }

    @Override
    public Color getFill() {
        return (Color)rectangle.getFill();
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        rectangle.setStrokeWidth(strokeWidth);
    }

    @Override
    public double getStrokeWidth() {
        return rectangle.getStrokeWidth();
    }

    @Override
    public void setStrokeColor(Color strokeColor) {
        rectangle.setStroke(strokeColor);

    }

    @Override
    public Color getStrokeColor() {
        return (Color)rectangle.getStroke();
    }

    @Override
    public Shape getNode() {
        return rectangle;
    }

    @Override
    public void setDefault() {
        x = Double.parseDouble(getDEFAULT_BUNDLE().getString("rectangle.x"));
        y = Double.parseDouble(getDEFAULT_BUNDLE().getString("rectangle.y"));
        width = Double.parseDouble(getDEFAULT_BUNDLE().getString("rectangle.width"));
        height = Double.parseDouble(getDEFAULT_BUNDLE().getString("rectangle.height"));
        Color fill;
        double strokeWidth;
        Color strokeColor;
        this.rectangle = new Rectangle(x, y, width, height);
    }
}