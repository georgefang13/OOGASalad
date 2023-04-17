package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Generates a random number between 0 and the given number
 */
public class Random extends OperatorToken {

  public Random() {
    super(1, "Random");
  }

  @Override
  public ValueToken<Double> evaluate(Environment env) {
    Token t = getArg(0).evaluate(env);

    ValueToken<Double> range = checkArgumentWithSubtype(env, t, ValueToken.class,
        Double.class.getName());

    double rand = Math.random() * range.VALUE;
    return new ValueToken<>(rand);
  }
}
