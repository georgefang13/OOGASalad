//package oogasalad.frontend.nodeEditor.customNodeEditor.Utils.Ports;
//import javafx.geometry.Point2D;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import oogasalad.frontend.nodeEditor.customNodeEditor.Utils.Connections.Connection;
//
//public class Port extends StackPane {
//
//    private Rectangle portShape;
//    private DraggableItem node;
//    private String fieldName;
//
//    public Port(double width, double height, DraggableItem node, String fieldName) {
//        this.node = node;
//        this.fieldName = fieldName;
//
//        portShape = new Rectangle(width, height);
//        portShape.setFill(Color.TRANSPARENT);
//        portShape.setStroke(Color.BLACK);
//
//        getChildren().addAll(portShape);
//
//        setOnMousePressed(event -> {
//            event.consume();
//        });
//
//        setOnMouseDragged(event -> {
//            Point2D localCoord = getParent().sceneToLocal(event.getSceneX(), event.getSceneY());
//            setLayoutX(localCoord.getX() - getWidth() / 2.0);
//            setLayoutY(localCoord.getY() - getHeight() / 2.0);
//            event.consume();
//        });
//
//        setOnMouseReleased(event -> {
//            for (Port otherPort : node.getPorts()) {
//                if (otherPort != this && otherPort.contains(otherPort.parentToLocal(getParent().localToParent(event.getX(), event.getY())))) {
//                    Connection connection = new Connection(this, otherPort);
////                    node.addConnection(connection);
//                    event.consume();
//                    return;
//                }
//            }
//            event.consume();
//        });
//    }
//
//    public DraggableItem getNode() {
//        return node;
//    }
//
//    public double getCenterX() {
//        return getLayoutX() + getWidth() / 2.0;
//    }
//
//    public double getCenterY() {
//        return getLayoutY() + getHeight() / 2.0;
//    }
//
//    public String getFieldName() {
//        return fieldName;
//    }
//
//    public Point2D getCenterPoint() {
//        return parentToLocal(getBoundsInParent().getWidth() / 2.0, getBoundsInParent().getHeight() / 2.0);
//    }
//}
