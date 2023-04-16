package oogasalad.frontend.objects;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagePiece implements PieceImage {

  private ImageView xImage;
  private final String DEFAULT_FILE_PATH = "frontend.properties.Defaults.GameObject";
  private final ResourceBundle DEFAULT_BUNDLE = ResourceBundle.getBundle(DEFAULT_FILE_PATH);

  public ImagePiece() {
    xImage = new ImageView();
  }

  @Override
  public void setProperties(String pieceName) {
    Image image = readImageFileFromFile(pieceName);
    xImage.setImage(image);
    xImage.setFitWidth(30);
    xImage.setFitHeight(30);
  }

  @Override
  public Node getPieceNode() {
    return xImage;
  }

  private Image readImageFileFromFile(String pieceName) {
    switch (pieceName) {
      case "X":
        return new Image(DEFAULT_BUNDLE.getString("X_IMAGE"));
      case "O":
        return new Image(DEFAULT_BUNDLE.getString("DEFAULT_IMAGE"));
      default:
        return new Image(DEFAULT_BUNDLE.getString("DEFAULT_IMAGE"));
    }
  }
}
