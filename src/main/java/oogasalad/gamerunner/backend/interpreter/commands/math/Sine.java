package oogasalad.gamerunner.backend.interpreter.commands.math;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the sine of the given value.
 */
public class Sine extends OperatorToken {

  public Sine() {
    super(1, "Sine");
  }

  @Override
  public Token evaluate(Environment env) {
    Token t = getArg(0).evaluate(env);

    ValueToken<Double> x1 = checkArgumentWithSubtype(env, t, ValueToken.class,
        Double.class.getName());

    return new ValueToken<>(Math.sin(x1.VALUE * Math.PI / 180.));
  }
}
