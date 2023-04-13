package oogasalad.frontend.objects;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public interface Piece {
    public void setProperties(String pieceName);
    public Circle getPieceImage();
    //public ImageView getImageView();
}
