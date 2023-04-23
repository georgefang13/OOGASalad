package oogasalad.gamerunner.backend.interpreter.exceptions;

public class CannotBeEvaluatedException extends RuntimeException {

  public CannotBeEvaluatedException(String msg) {
    super(msg);
  }
}
