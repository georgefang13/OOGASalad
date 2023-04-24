package oogasalad.frontend.components.ParameterStrategies;

import oogasalad.frontend.components.ParameterStrategy;

public class DoubleParameterStrategy implements ParameterStrategy<Double> {
    @Override
    public Double convert(String input) {
        return Double.parseDouble(input);
    }
}
