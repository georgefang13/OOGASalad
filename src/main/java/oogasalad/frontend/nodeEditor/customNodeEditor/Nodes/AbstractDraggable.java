package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import oogasalad.frontend.nodeEditor.customNodeEditor.Draggable;

public class AbstractDraggable extends OogaNode implements Draggable {

    public AbstractDraggable(double x, double y, double width, double height, String color) {
        super(x, y, width, height, color);
    }

    @Override
    public void onDragDetected() {

        setOnMouseDragged(e -> {
            setTranslateX(e.getSceneX() - xOffset);
            setTranslateY(e.getSceneY() - yOffset);
        });

    }

    @Override
    public void onMousePressed() {
        this.setOnMousePressed(e -> {
            xOffset = e.getSceneX() - (getTranslateX());
            yOffset = e.getSceneY() - (getTranslateY());
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
}
