package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Repeats the given expressions the given number of times
 */
public class If extends OperatorToken {

  public If() {
    super(2, "If");
  }

  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t = getArg(0).evaluate(env);

    ValueToken<Boolean> b = checkArgumentWithSubtype(env, t, ValueToken.class,
        Boolean.class.getName());
    ExpressionToken exprs = checkArgument(env, getArg(1), ExpressionToken.class);

    Token result = null;
    if (b.VALUE) {
      result = exprs.evaluate(env);
    }

    return result;
  }
}
