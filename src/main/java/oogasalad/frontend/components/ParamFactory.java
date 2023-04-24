package oogasalad.frontend.components;

import javafx.scene.image.ImageView;
import oogasalad.frontend.components.ParameterStrategies.BooleanParameterStrategy;
import oogasalad.frontend.components.ParameterStrategies.ImageViewParameterStrategy;
import oogasalad.frontend.components.ParameterStrategies.StringParameterStrategy;

public class ParamFactory {
  public static ConversionContext<?> createConversionContext(Class<?> outputClass) {
    if (outputClass == Boolean.class) {
      return new ConversionContext<>(new BooleanParameterStrategy());
    } else if (outputClass == ImageView.class) {
      return new ConversionContext<>(new ImageViewParameterStrategy());
    } else if (outputClass == String.class) {
      return new ConversionContext<>(new StringParameterStrategy());
    } else {
      throw new IllegalArgumentException("Unsupported output class type: " + outputClass);
    }
  }
}