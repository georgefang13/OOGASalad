package oogasalad.frontend.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BasicPiece implements Piece{
    private Circle circleImage; //for now

    public BasicPiece(String pieceName) {
        circleImage = new Circle();
        setProperties(pieceName);
    }

    @Override
    public void setProperties(String pieceName) {
        circleImage.setRadius(25);
        circleImage.setFill(readColorFromFile(pieceName));
    }
    @Override
    public Circle getPieceImage() {
        return circleImage;
    }

    //for now
    private Color readColorFromFile(String pieceName){
        switch (pieceName){
            case "X":
                return Color.BLACK;
            case "O":
                return Color.RED;
            default:
                return Color.GREEN;
        }
    }
}
