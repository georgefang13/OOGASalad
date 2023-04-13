package oogasalad.frontend.nodeEditor.myNodeEditor;

import javafx.scene.input.DataFormat;

import java.io.Serializable;

public class DraggableContainer implements Serializable {
    public static final DataFormat RECTANGLE_FORMAT = new DataFormat("application/x-myapp-rectangle");
    private double x;
    private double y;
    private double width;
    private double height;
    private String color;
    private SerializableNodeList children;
//    private DraggableItem ogItem;

    private double dragX;
    private double dragY;

    public DraggableContainer(DraggableItem item) {
        this.x = item.getX();
        this.y = item.getY();
        this.width = item.getWidth();
        this.height = item.getHeight();
        this.color = item.getColor();
        this.dragX = 0;
        this.dragY = 0;
        this.children = new SerializableNodeList(item.getChildren());
//        this.ogItem = item;
//        item.setVisible(false);
        System.out.println(children);
    }
    
    public void setDragX(double x) {
        dragX = x;
    }

    public void setDragY(double y) {
        dragY = y;
    }

    public double getDragX() {
        return dragX;
    }

    public double getDragY() {
        return dragY;
    }
}
