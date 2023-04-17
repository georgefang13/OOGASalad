//package oogasalad.frontend.nodeEditor.customNodeEditor.Utils.Connections;
//
//import javafx.scene.shape.Line;
//import oogasalad.frontend.nodeEditor.customNodeEditor.Utils.Ports.Port;
//
//public class Connection extends Line {
//    private Port startPort;
//    private Port endPort;
//
//    public Connection(Port startPort, Port endPort) {
//        super(startPort.getCenterX(), startPort.getCenterY(), endPort.getCenterX(), endPort.getCenterY());
//        this.startPort = startPort;
//        this.endPort = endPort;
//
//        // Set the start and end points of the line based on the ports
////        startPort.centerXProperty().addListener((observable, oldValue, newValue) -> {
////            setStartX(startPort.getCenterX());
////            setStartY(startPort.getCenterY());
////        });
////
////        startPort.centerYProperty().addListener((observable, oldValue, newValue) -> {
////            setStartX(startPort.getCenterX());
////            setStartY(startPort.getCenterY());
////        });
////
////        endPort.centerXProperty().addListener((observable, oldValue, newValue) -> {
////            setEndX(endPort.getCenterX());
////            setEndY(endPort.getCenterY());
////        });
////
////        endPort.centerYProperty().addListener((observable, oldValue, newValue) -> {
////            setEndX(endPort.getCenterX());
////            setEndY(endPort.getCenterY());
////        });
//    }
//
//
//
//    public Port getStartPort() {
//        return startPort;
//    }
//
//    public Port getEndPort() {
//        return endPort;
//    }
//}
