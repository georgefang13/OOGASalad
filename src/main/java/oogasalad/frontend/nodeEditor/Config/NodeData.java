package oogasalad.frontend.nodeEditor.Config;

import java.io.Serializable;

public class NodeData implements Serializable {
    private double x, y, width, height, indent;

    public NodeData(double x, double y, double width, double height, double indent) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.indent = indent;
    }

    // Getter and setter methods for all fields
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getIndent() {
        return indent;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y= y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setIndent(double indent) {
        this.indent = indent;
    }

}

