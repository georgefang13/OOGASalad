package oogasalad.frontend.components;

public class ConversionContext<T> {
  private final ParameterStrategy<T> strategy;

  public ConversionContext(ParameterStrategy<T> strategy) {
    this.strategy = strategy;
  }

  public T convert(String input) {
    return strategy.convert(input);
  }
}