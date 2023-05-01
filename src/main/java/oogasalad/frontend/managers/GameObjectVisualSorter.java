package oogasalad.frontend.managers;

import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.DropZoneVisual;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.PieceVisual;
import java.util.Comparator;
/**
 * @author Owen MacKenzie
 */
public class GameObjectVisualSorter implements Comparator{
    @Override
    public int compare(Object obj1, Object obj2) {
        Node node1 = (Node) obj1;
        Node node2 = (Node) obj1;
        if (node1 instanceof DropZoneVisual && node2 instanceof PieceVisual) {
            return -1;
        } else if (node2 instanceof DropZoneVisual && node1 instanceof PieceVisual) {
            return 1;
        } else {
            return 0;
        }
    }
}
