package oogasalad.frontend.managers;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;
/**
 * @author Owen MacKenzie
 */
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
            if (c.wasAdded()) {
                System.out.println("detected node added");
                List<? extends Node> addedNodes = c.getAddedSubList();
                System.out.println(root.getChildren().size());
                root.getChildren().addAll(addedNodes);
                System.out.println(root.getChildren().size());
            }
        }
    }
}
