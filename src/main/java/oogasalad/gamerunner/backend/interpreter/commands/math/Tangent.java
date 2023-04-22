package oogasalad.gamerunner.backend.interpreter.commands.math;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the tangent of the given value.
 */
public class Tangent extends OperatorToken {

  public Tangent() {
    super(1, "Tangent");
  }

  @Override
  public Token evaluate(Environment env) {
    Token t = getArg(0).evaluate(env);

    ValueToken<Double> x1 = checkArgumentWithSubtype(env, t, ValueToken.class,
        Double.class.getName());

    return new ValueToken<>(Math.tan(x1.VALUE * Math.PI / 180.));
  }
}
