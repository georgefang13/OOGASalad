package oogasalad.frontend.components.gameObjectComponent.GameRunner.Board;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.Board.BoardCell;

public class UnusedCell implements BoardCell {
    private Rectangle square;
    private StackPane dropVisual;
    private int blockSize;
    public UnusedCell(int blockSize) {
        dropVisual = new StackPane();
        this.blockSize = blockSize;
        initializeSquare();
    }

    @Override
    public StackPane getDropZoneVisual() {
        return dropVisual;
    }
    private void initializeSquare() {
        square = new Rectangle(blockSize, blockSize);
        square.setFill(Color.WHITE);
        dropVisual.getChildren().add(square);
    }
}
