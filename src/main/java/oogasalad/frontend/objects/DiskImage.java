package oogasalad.frontend.objects;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DiskImage implements PieceImage {

  private Circle diskImage;

  public DiskImage() {
    diskImage = new Circle();
  }

  @Override
  public void setProperties(String pieceName) {
    diskImage.setRadius(25);
    diskImage.setFill(readColorFromFile(pieceName));
  }

  @Override
  public Node getPieceNode() {
    return diskImage;
  }

  private Color readColorFromFile(String pieceName) {
    switch (pieceName) {
      case "X":
        return Color.BLACK;
      case "O":
        return Color.RED;
      default:
        return Color.GREEN;
    }
  }
}
