package oogasalad.frontend.components.lineObjectComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import oogasalad.frontend.components.AbstractComponent;
import java.util.Map;

public class LineObject extends AbstractComponent implements LineObjectComponent {
    private String name;
    private Line myLine;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private Color color;
    private double strokeWidth;
    public LineObject(String ID, Map<String, String> map){
        super(ID);
        setDraggable(true);
        setValuesfromMap(map);
        initialize();
        followMouse();
    }

    private void initialize() {
        myLine = new Line(startX,startY,endX, endY);
        myLine.setStroke(color);
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
    public Color getColor() {
        return (Color)myLine.getFill();
    }

    @Override
    public void setColor(Color color) {
        myLine.setFill(color);
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
}
