package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes if x == y
 */
public class Equal extends OperatorToken {

  public Equal() {
    super(2, "Equal");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    boolean b = getArg(0).equals(getArg(1), env);
    return new ValueToken<>(b);
  }
}
