package oogasalad.frontend.nodeEditor.myNodeEditor;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class DraggableController {

    private DraggableItem item;
    private double mouseDownX;
    private double mouseDownY;

    public DraggableController(DraggableItem item) {
        this.item = item;

        item.setOnMousePressed(event -> {
            mouseDownX = event.getX();
            mouseDownY = event.getY();
        });
        item.setOnDragDetected(event -> {
            Dragboard dragboard = item.startDragAndDrop(TransferMode.MOVE);
            DraggableContainer container = new DraggableContainer(item);

            container.setDragX(event.getX() - item.getLayoutX());
            container.setDragY(event.getY() - item.getLayoutY());
            dragboard.setDragView(item.snapshot(null, null));
            dragboard.setContent(Map.of(container.RECTANGLE_FORMAT, container));
            event.consume();
        });
    }

}
