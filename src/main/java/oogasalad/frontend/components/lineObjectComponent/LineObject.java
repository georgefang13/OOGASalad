package oogasalad.frontend.components.lineObjectComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import oogasalad.frontend.components.AbstractComponent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class LineObject extends AbstractComponent implements LineObjectComponent {
    private Line myLine;
    private final String DEFAULT_FILE_PATH = "frontend.properties.Defaults.LineObject";
    private ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

    public LineObject(int ID){
        super(ID);
        this.setDefault();
        this.followMouse();
        this.getNode();
    }

    public LineObject(int ID, Node container){
        super(ID, container);
        this.setDefault();
    }

    public LineObject(int ID, Node container, double startX, double startY, double endX, double endY, String color, double strokeWidth) {
        super(ID,container);
        myLine = new Line(startX,startY,endX,endY);
        myLine.setFill(Color.BLACK);
        myLine.setStrokeWidth(strokeWidth);
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
        double startX = Double.parseDouble(DEFAULT_BUNDLE.getString("line.startX"));
        double startY = Double.parseDouble(DEFAULT_BUNDLE.getString("line.startY"));
        double endX = Double.parseDouble(DEFAULT_BUNDLE.getString("line.endX"));
        double endY = Double.parseDouble(DEFAULT_BUNDLE.getString("line.endY"));
        String color = DEFAULT_BUNDLE.getString("line.color");
        double strokeWidth = Double.parseDouble(DEFAULT_BUNDLE.getString("line.strokeWidth"));

        myLine = new Line(startX,startY,endX,endY);
        myLine.setFill(Color.BLACK);
        myLine.setStrokeWidth(strokeWidth);
    }
}
