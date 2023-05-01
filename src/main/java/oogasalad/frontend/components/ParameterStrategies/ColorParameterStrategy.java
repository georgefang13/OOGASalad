package oogasalad.frontend.components.ParameterStrategies;

import javafx.scene.paint.Color;
import oogasalad.frontend.components.ParameterStrategy;

public class ColorParameterStrategy implements ParameterStrategy<Color> {
        @Override
        public Color convert(String input) {
            return Color.web(input);
        }
}
