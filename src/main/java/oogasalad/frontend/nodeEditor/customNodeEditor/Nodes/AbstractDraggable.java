package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import oogasalad.frontend.nodeEditor.customNodeEditor.Draggable;

public class AbstractDraggable extends OogaNode implements Draggable {

    public AbstractDraggable(double x, double y, double width, double height, String color) {
        super(x, y, width, height, color);
    }

    @Override
    public void onDragDetected() {

    }

    @Override
    public void onMousePressed() {

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
}
