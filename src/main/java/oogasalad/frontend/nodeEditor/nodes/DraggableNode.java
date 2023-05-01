package oogasalad.frontend.nodeEditor.nodes;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
/**
 * This interface is used to mark nodes that are draggable in the NodeEditor.
 */
public interface DraggableNode {

    void snapToNode(AbstractNode node);

    void alignNodes(AbstractNode fromNode, AbstractNode toNode);

    void onDragDetected();

    void onMousePressed();

    void onMouseDragged();

    void onMouseReleased();

    void move(double newX, double newY);
}
