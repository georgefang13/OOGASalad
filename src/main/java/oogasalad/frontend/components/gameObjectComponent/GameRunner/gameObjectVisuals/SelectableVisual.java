package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

import javafx.scene.Node;

public interface SelectableVisual {
    void showClickable();
    void showUnclickable();
    String getObjectID();
    void updateVisual(Node newVisual);
}