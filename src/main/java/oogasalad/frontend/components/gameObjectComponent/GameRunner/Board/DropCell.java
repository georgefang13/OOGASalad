package oogasalad.frontend.components.gameObjectComponent.GameRunner.Board;

import oogasalad.frontend.components.gameObjectComponent.GameRunner.Board.BoardCell;

public interface DropCell extends BoardCell {
  void assignDropZoneID(String id);
  String getDropZoneID();
}
