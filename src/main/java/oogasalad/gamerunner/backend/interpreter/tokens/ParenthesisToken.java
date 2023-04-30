package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.exceptions.CannotBeEvaluatedException;

public class ParenthesisToken extends Token implements OpenCloseToken {

  public final String VALUE;

  public ParenthesisToken(String value) {
    super("Parenthesis", value);
    VALUE = value;
  }

  @Override
  public Token evaluate(Environment env) {
    throw new CannotBeEvaluatedException("Cannot evaluate a token of type Parenthesis");
  }

  @Override
  public ParenthesisToken copy(){
    return new ParenthesisToken(VALUE);
  }

  @Override
  public String toString() {
    return String.format("<%s %s = %s>", TYPE, VALUE);
  }

  /**
   * returns true if the bracket is an open parenthesis ( and false if it is a close parenthesis )
   *
   * @return boolean if parenthesis is open
   */
  @Override
  public boolean isOpen() {
    return VALUE.equals("(");
  }
}