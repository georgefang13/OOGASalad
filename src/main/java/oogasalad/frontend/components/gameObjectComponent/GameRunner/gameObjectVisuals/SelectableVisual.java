package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

/**
 * This interface is used to allow the GameObjectVisual to be selectable by the user. This is used
 * in the GameRunner to allow the user to select a GameObjectVisual and then edit its properties.
 */
public interface SelectableVisual {
    void showClickable();
    void showUnclickable();
    String getObjectID();
    void updateClickableVisual(String param);
}
