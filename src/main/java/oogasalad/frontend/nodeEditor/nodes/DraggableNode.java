package oogasalad.frontend.nodeEditor.nodes;


public interface DraggableNode {

    void snapToNode(AbstractNode node);

    void alignNodes(AbstractNode fromNode, AbstractNode toNode);

    void onDragDetected();

    void onMousePressed();

    void onMouseDragged();

    void onMouseReleased();

    void move(double newX, double newY);
}
