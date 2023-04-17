package oogasalad.frontend.objects;

public class BasicPiece implements Piece {

  private PieceImage pieceImage;


  public BasicPiece(String pieceName) {
    pieceImage = createPieceImage(pieceName);
    pieceImage.setProperties(pieceName);
  }

  @Override
  public PieceImage getPieceImage() {
    return pieceImage;
  }

  private PieceImage createPieceImage(String pieceName) {
    PieceShapes pieceShape = readPieceShapeFromFile(pieceName);
    switch (pieceShape) {
      case IMAGE:
        return new ImagePiece();
      case RECTANGLE:
        return new DiskImage();
      case DISK:
        return new DiskImage();
      default:
        return new DiskImage();
    }
  }

  enum PieceShapes {
    IMAGE,
    DISK,
    RECTANGLE
  }

  private PieceShapes readPieceShapeFromFile(String pieceName) {
    switch (pieceName) {
      case "X":
        return PieceShapes.IMAGE;
      case "O":
        return PieceShapes.IMAGE;
      default:
        return PieceShapes.RECTANGLE;
    }
  }


}
