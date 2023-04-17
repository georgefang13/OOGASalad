package oogasalad.gamerunner.backend.interpreter.exceptions;

public class InvalidSyntaxException extends RuntimeException {

  public InvalidSyntaxException(String val) {
    super("Invalid syntax: " + val);
  }
}
