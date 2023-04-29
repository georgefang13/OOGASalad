package oogasalad.frontend.nodeEditor;

import oogasalad.frontend.nodeEditor.Nodes.AbstractNode;

public interface Draggable {

    void snapTo(AbstractNode node);

    void adjust(AbstractNode fromNode, AbstractNode toNode);

    void onDragDetected();

    void onMousePressed();

    void onMouseDragged();

    void onMouseReleased();

    void move(double newX, double newY);
}
