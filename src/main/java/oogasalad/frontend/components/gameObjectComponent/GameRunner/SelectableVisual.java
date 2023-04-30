package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public interface SelectableVisual {
    void showClickable();
    void showUnclickable();
    String getObjectID();
    void updateVisual(Node newVisual);
}
