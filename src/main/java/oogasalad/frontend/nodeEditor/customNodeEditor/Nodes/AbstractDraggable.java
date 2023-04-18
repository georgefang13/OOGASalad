package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import oogasalad.frontend.nodeEditor.customNodeEditor.Draggable;

public class AbstractDraggable extends OogaNode implements Draggable {

    public AbstractDraggable(double x, double y, double width, double height, String color) {
        super(x, y, width, height, color);
        onDragDetected();
        onMousePressed();
        onMouseDragged();

    }

    @Override
    public void onDragDetected() {
        this.setOnDragDetected(event -> {
            startFullDrag();
            event.consume();
        });
    }

    @Override
    public void onMousePressed() {
        this.setOnMousePressed(e -> {
            xOffset = e.getSceneX() - (getTranslateX());
            yOffset = e.getSceneY() - (getTranslateY());
            if (e.isShiftDown()) {
                this.delete();
            }
        });

    }

    @Override
    public void onMouseDragged() {
        this.setOnMouseDragged(e -> {
            setTranslateX(e.getSceneX() - xOffset);
            setTranslateY(e.getSceneY() - yOffset);
        });

    }

    @Override
    protected void setContent() {

    }

    @Override
    public String sendContent() {
        return null;
    }
}
