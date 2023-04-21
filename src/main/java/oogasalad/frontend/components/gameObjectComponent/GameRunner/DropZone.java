package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.layout.StackPane;

public interface DropZone {

  public StackPane getDropZoneVisual();

  public void addPiece(BoardPiece boardPiece);
}
