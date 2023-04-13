package oogasalad.frontend.objects;

import javafx.scene.layout.GridPane;

public class Board {
    private int height;
    private int width;
    private DropZone[][] boardDrops;
    private GridPane boardPane;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        initializeBoard();
    }

    private void initializeBoard() {
        boardPane = new GridPane();
        boardDrops = new DropSquare[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                DropZone gridSquare = new DropSquare(80);
                boardDrops[row][col] = gridSquare;
                boardPane.add(gridSquare.getDropZoneVisual(), col, row);
            }
        }
    }

    public GridPane getBoardVisual() {
        return boardPane;
    }

    public void addPiece(int x, int y, String pieceName){
        DropZone pieceSquare = boardDrops[x][y];
        pieceSquare.addPiece(pieceName);
    }

    record dropXY(double x, double y) {
    }
}
