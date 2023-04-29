package oogasalad.gamerunner.backend.interpreter.tokens;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.exceptions.CannotBeEvaluatedException;

public class BracketToken extends Token implements OpenCloseToken {

  public final String NAME;
  public final String VALUE;

  public BracketToken(String name, String value) {
    super("Bracket", value);
    NAME = name;
    VALUE = value;
  }

  @Override
  public Token evaluate(Environment env) {
    throw new CannotBeEvaluatedException("Cannot evaluate a token of type Bracket");
  }

  @Override
  public String toString() {
    return String.format("<%s %s = %s>", TYPE, NAME, VALUE);
  }

  @Override
  public BracketToken copy(){
    return new BracketToken(NAME, VALUE);
  }

  /**
   * returns true if the bracket is an open bracket ( [ ) and false if it is a close bracket ( ] )
   *
   * @return boolean if bracket is open
   */
  @Override
  public boolean isOpen() {
    return VALUE.equals("[");
  }
}
