package oogasalad.gamerunner.backend.interpreter.exceptions;

public class TokenNotRecognizedException extends RuntimeException {

  public TokenNotRecognizedException(String val) {
    super("Token not recognized: " + val);
  }

}
