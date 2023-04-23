package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes if x >= y
 */
public class GreaterEqual extends OperatorToken {

  public GreaterEqual() {
    super(2, "GreaterEqual");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<Double> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class,
        Double.class.getName());
    ValueToken<Double> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class,
        Double.class.getName());

    return new ValueToken<>(x1.VALUE >= x2.VALUE);
  }
}
