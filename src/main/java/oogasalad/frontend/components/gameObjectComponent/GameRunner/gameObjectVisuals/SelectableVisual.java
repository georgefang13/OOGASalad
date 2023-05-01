package oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals;

/**
 * @author Owen MacKenzie
 */
public interface SelectableVisual {
    void showClickable();
    void showUnclickable();
    String getObjectID();
    void updateClickableVisual(String param);
}
