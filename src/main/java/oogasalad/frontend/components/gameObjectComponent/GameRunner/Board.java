package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;

public class Board {

  private int height;
  private int width;
  private BoardCell[][] boardCells;
  private GridPane boardPane;
  private int blockSize;

  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    initializeEmptyBoard();
  }

  private void initializeEmptyBoard() {
    boardPane = new GridPane();
    boardCells = new BoardCell[height][width];
    blockSize = readBlockSize();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        BoardCell boardCell = new UnusedCell(blockSize);
        addBoardCell(boardCell,row,col);
      }
    }
  }

  public void addBoardCell(BoardCell boardCell, int row, int col){
    boardCells[row][col] = boardCell;
    boardPane.add(boardCell.getDropZoneVisual(), col, row);
  }

  private int readBlockSize() {
    return 80;
  }

  public GridPane getBoardVisual() {
    return boardPane;
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
