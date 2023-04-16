package oogasalad.frontend.objects;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DropSquare implements DropZone {

  private Rectangle square;
  private StackPane dropVisual;
  private int blockSize;

  public DropSquare(int blockSize) {
    dropVisual = new StackPane();
    this.blockSize = blockSize;
    initializeSquare();
  }

  private void initializeSquare() {
    square = new Rectangle(blockSize, blockSize);
    square.setFill(Color.SKYBLUE);
    square.setStroke(Color.BLACK);
    dropVisual.getChildren().add(square);
  }

  @Override
  public void addPiece(String pieceName) {
    Piece newPiece = new BasicPiece(pieceName);
    if (dropVisual.getChildren().size() > 1) {
      dropVisual.getChildren().remove(1);
    }
    dropVisual.getChildren().add(newPiece.getPieceImage().getPieceNode());
  }

  @Override
  public StackPane getDropZoneVisual() {
    return dropVisual;
  }
}
