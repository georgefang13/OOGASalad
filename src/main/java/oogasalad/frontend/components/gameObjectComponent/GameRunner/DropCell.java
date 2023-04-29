package oogasalad.frontend.components.gameObjectComponent.GameRunner;

public interface DropCell extends BoardCell{
  void assignDropZoneID(String id);
  String getDropZoneID();
}
