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
        System.out.println(children);
    }

    // private void readObject(ObjectInputStream in) throws IOException,
    // ClassNotFoundException {
    // in.defaultReadObject(); // read non-transient fields
    // children = ; // initialize the transient field
    // }
//    private void writeObject(ObjectOutputStream out) throws IOException {
//        out.defaultWriteObject();
//        out.writeObject(children);
//    }
//
//    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//        in.defaultReadObject();
//        children = (SerializableNodeList) in.readObject();
//    }

    public DraggableItem getItem() {
        DraggableItem item = new DraggableItem(x, y, width, height, color, children.getNodes());
//        item.getChildren().clear();
//        item.getChildren().addAll();
        return item;
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
