package oogasalad.frontend.components;

public interface ParameterStrategy<T> {
  T convert(String input);
}

