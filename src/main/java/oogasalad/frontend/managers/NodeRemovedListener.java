package oogasalad.frontend.managers;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

public class NodeRemovedListener implements ListChangeListener<Node> {
    private Pane root;
    public NodeRemovedListener(Pane root) {
        this.root = root;
    }
    @Override
    public void onChanged(Change c) {
        while (c.next()) {
            if (c.wasRemoved()) {
                List<? extends Node> removedNodes = c.getRemoved();
                root.getChildren().removeAll(removedNodes);
            }
        }
    }
}
