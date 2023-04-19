package oogasalad.frontend.nodeEditor.customNodeEditor.Nodes;

import javafx.scene.Node;
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

             /*check if it is on top of another node
              * if so, snap to it
              * if not, do nothing
             */
            for (Node node: this.getParent().getChildrenUnmodifiable()) {
                if (node instanceof OogaNode) {
                    if (this.getBoundsInParent().intersects(node.getBoundsInParent())) {
                        snapTo((OogaNode) node);
                    }
                }

            }





        });

    }

    protected void snapTo(OogaNode node) {
        this.setTranslateX(node.getTranslateX());
        this.setTranslateY(node.getTranslateY());
    }

    @Override
    protected void setContent() {

    }

    @Override
    public String sendContent() {
        return null;
    }
}
