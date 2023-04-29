package oogasalad.frontend.components;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import oogasalad.frontend.components.ParameterStrategies.*;

public class ParamFactory {
  public static ConversionContext<?> createConversionContext(Class<?> outputClass) {
    if (outputClass == Boolean.class) {
      return new ConversionContext<>(new BooleanParameterStrategy());
    } else if (outputClass == ImageView.class) {
      return new ConversionContext<>(new ImageViewParameterStrategy());
    } else if (outputClass == String.class) {
      return new ConversionContext<>(new StringParameterStrategy());
    } else if (outputClass == double.class) {
      return new ConversionContext<>(new DoubleParameterStrategy());
    } else if (outputClass == int.class) {
      return new ConversionContext<>(new IntegerParameterStrategy());
    } else if (outputClass == Color.class) {
      return new ConversionContext<>(new ColorParameterStrategy());
    }
    else {
      throw new IllegalArgumentException("Unsupported output class type: " + outputClass);
    }
  }
}