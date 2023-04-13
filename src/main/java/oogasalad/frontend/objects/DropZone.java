package oogasalad.frontend.objects;

import javafx.scene.layout.StackPane;

public interface DropZone {
    public StackPane getDropZoneVisual();
    public void addPiece(String pieceName);
}
