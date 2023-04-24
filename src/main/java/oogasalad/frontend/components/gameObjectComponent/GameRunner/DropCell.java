package oogasalad.frontend.components.gameObjectComponent.GameRunner;

public interface DropCell extends BoardCell{
  public void assignDropZoneID(String id);
  public String getDropZoneID();
}
