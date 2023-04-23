package oogasalad.gamerunner.backend.interpreter.exceptions;

public class IncorrectArgumentCountException extends RuntimeException {

  public IncorrectArgumentCountException(String name, int actual, int expected) {
    super("Incorrect number of arguments passed to " + name + ": found " + actual + ", expected "
        + expected);
  }
}
