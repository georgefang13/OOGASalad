package oogasalad.frontend.components.ParameterStrategies;

import oogasalad.frontend.components.ParameterStrategy;

public class StringParameterStrategy implements ParameterStrategy<String> {

  @Override
  public String convert(String input) {
    return input;
  }

}
