package oogasalad.gamerunner.backend.interpreter.commands.math;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the negation of a number
 */
public class Minus extends OperatorToken {

  public Minus() {
    super(1, "Minus");
  }

  @Override
  public Token evaluate(Environment env) {
    Token t = getArg(0).evaluate(env);

    ValueToken<Double> x1 = checkArgumentWithSubtype(env, t, ValueToken.class,
        Double.class.getName());

    return new ValueToken<>(-x1.VALUE);
  }
}
