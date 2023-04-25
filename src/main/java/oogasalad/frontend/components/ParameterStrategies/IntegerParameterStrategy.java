package oogasalad.frontend.components.ParameterStrategies;

import oogasalad.frontend.components.ParameterStrategy;

public class IntegerParameterStrategy implements ParameterStrategy<Integer> {
    @Override
    public Integer convert(String input) {
        return Integer.parseInt(input);
    }
}
