package oogasalad.frontend.components.ParameterStrategies;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oogasalad.frontend.components.ParameterStrategy;

public class ImageViewParameterStrategy implements ParameterStrategy<ImageView> {
  @Override
  public ImageView convert(String input) {
    Image image = new Image(new File(input).toURI().toString());
    ImageView imageView = new ImageView(image);
    return imageView;
  }
}