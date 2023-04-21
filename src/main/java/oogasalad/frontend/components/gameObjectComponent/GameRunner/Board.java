package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;

public class Board {

  private int height;
  private int width;
  private DropZone[][] boardDrops;
  private GridPane boardPane;
  private int blockSize;

  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    initializeBoard();
  }

  private void initializeBoard() {
    boardPane = new GridPane();
    boardDrops = new DropSquare[height][width];
    blockSize = readBlockSize();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        DropZone gridSquare = new DropSquare(blockSize);
        boardDrops[row][col] = gridSquare;
        boardPane.add(gridSquare.getDropZoneVisual(), col, row);
      }
    }
  }

  private int readBlockSize() {
    return 80;
  }

  public GridPane getBoardVisual() {
    return boardPane;
  }

  public void addPiece(int x, int y, BoardPiece boardPiece) {
    DropZone pieceSquare = boardDrops[x][y];
    pieceSquare.addPiece(boardPiece);
  }

  public record BoardXY(int x, int y) {
  }

  public BoardXY boardXYofNode(Point2D nodeXY) {
    int x = (int) (nodeXY.getX() / blockSize);
    int y = (int) (nodeXY.getY() / blockSize);
    if (!checkValid(x,width) || !checkValid(y,height)){
      x = -1;
      y = -1;
    }
    return new BoardXY(x, y);
  }

  private boolean checkValid(int xy, int max){
    return ((0 <= xy) && (xy <= max));
  }
}
