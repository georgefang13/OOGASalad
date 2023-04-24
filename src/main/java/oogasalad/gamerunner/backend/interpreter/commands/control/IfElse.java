package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Repeats the given expressions the given number of times
 */
public class IfElse extends OperatorToken {

  public IfElse() {
    super(3, "IfElse");
  }

  public Token evaluate(Environment env) throws IllegalArgumentException {

    Token t = getArg(0).evaluate(env);

    ExpressionToken exprs1 = checkArgument(env, getArg(1), ExpressionToken.class);
    ExpressionToken exprs2 = checkArgument(env, getArg(2), ExpressionToken.class);
    ValueToken<Boolean> b = checkArgumentWithSubtype(env, t, ValueToken.class,
        Boolean.class.getName());

    Token result;

    if (b.VALUE) {
      result = exprs1.evaluate(env);
    } else {
      result = exprs2.evaluate(env);
    }

    return result;
  }
}
