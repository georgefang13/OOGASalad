package oogasalad.gamerunner.backend.interpreter.exceptions;


import oogasalad.gamerunner.backend.interpreter.tokens.Token;

public class IllegalTokenTypeException extends RuntimeException {

  public IllegalTokenTypeException(Token val) {
    super("Token type not allowed: " + val.TYPE);
  }

  public IllegalTokenTypeException(Token val, Token op) {
    super("Token type not allowed for operator " + op.SUBTYPE + ": " + val.TYPE);
  }

  public IllegalTokenTypeException(String errorMsg) {
    super(errorMsg);
  }
}
