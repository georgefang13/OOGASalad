package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DropZone implements DropCell {

  private Rectangle square;
  private StackPane dropVisual;
  private int blockSize;
  private String id;

  public DropZone(String DZid, int blockSize) {
    dropVisual = new StackPane();
    this.blockSize = blockSize;
    initializeSquare();
    assignDropZoneID(DZid);
  }

  private void initializeSquare() {
    square = new Rectangle(blockSize, blockSize);
    square.setFill(Color.SKYBLUE);
    square.setStroke(Color.BLACK);
    dropVisual.getChildren().add(square);
  }

  @Override
  public void assignDropZoneID(String id) {
    this.id = id;
  }

  @Override
  public String getDropZoneID() {
    return id;
  }

  @Override
  public StackPane getDropZoneVisual() {
    return dropVisual;
  }
}
