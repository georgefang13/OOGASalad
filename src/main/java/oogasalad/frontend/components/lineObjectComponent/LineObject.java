package oogasalad.frontend.components.lineObjectComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import oogasalad.Controller.ConvertingStrategy;
import oogasalad.frontend.components.AbstractComponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class LineObject extends AbstractComponent implements LineObjectComponent {
    private Line myLine;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private String color;
    private double strokeWidth;

    public LineObject(int ID, Map<String, String> map){
        super(ID);
        instantiatePropFile("frontend.properties.Defaults.LineObject");
        setValuesfromMap(map);
        myLine = new Line(startX,startY,endX, endY);
        followMouse();
    }

    @Override
    public double getStartX() {
        return myLine.getStartX();
    }

    @Override
    public void setStartX(double startX) {
        myLine.setStartX(startX);
    }

    @Override
    public double getStartY() {
        return myLine.getStartY();
    }

    @Override
    public void setStartY(double startY) {
        myLine.setStartY(startY);
    }

    @Override
    public double getEndX() {
        return myLine.getEndX();
    }

    @Override
    public void setEndX(double endX) {
        myLine.setEndX(endX);
    }

    @Override
    public double getEndY() {
        return myLine.getEndY();
    }

    @Override
    public void setEndY(double endY) {
        myLine.setEndY(endY);
    }

    @Override
    public String getColor() {
        return "hi";
    }

    @Override
    public void setColor(String color) {
    }

    @Override
    public double getStrokeWidth() {
        return myLine.getStrokeWidth();
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        myLine.setStrokeWidth(strokeWidth);
    }

    @Override
    public Node getNode(){
        return myLine;
    }

    @Override
    public void setDefault() {
        startX = Double.parseDouble(getDEFAULT_BUNDLE().getString("line.startX"));
        startY = Double.parseDouble(getDEFAULT_BUNDLE().getString("line.startY"));
        endX = Double.parseDouble(getDEFAULT_BUNDLE().getString("line.endX"));
        endY = Double.parseDouble(getDEFAULT_BUNDLE().getString("line.endY"));
        color = getDEFAULT_BUNDLE().getString("line.color");
        strokeWidth = Double.parseDouble(getDEFAULT_BUNDLE().getString("line.strokeWidth"));

        myLine = new Line(startX,startY,endX,endY);
        myLine.setFill(Color.BLACK);
        myLine.setStrokeWidth(strokeWidth);
    }
}
