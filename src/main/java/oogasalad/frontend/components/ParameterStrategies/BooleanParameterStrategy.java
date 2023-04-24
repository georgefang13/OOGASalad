package oogasalad.frontend.components.ParameterStrategies;

import oogasalad.frontend.components.ParameterStrategy;

public class BooleanParameterStrategy implements ParameterStrategy<Boolean> {
  @Override
  public Boolean convert(String input) {
    return Boolean.parseBoolean(input);
  }
}
