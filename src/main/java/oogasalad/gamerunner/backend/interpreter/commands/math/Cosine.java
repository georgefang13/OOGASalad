package oogasalad.gamerunner.backend.interpreter.commands.math;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the cosine of the given value.
 */
public class Cosine extends OperatorToken {

  public Cosine() {
    super(1, "Cosine");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t = getArg(0).evaluate(env);

    ValueToken<Double> x1 = checkArgumentWithSubtype(env, t, ValueToken.class,
        Double.class.getName());

    return new ValueToken<>(Math.cos(x1.VALUE * Math.PI / 180.));
  }
}
