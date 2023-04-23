package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Generates a random number between two numbers
 */
public class RandomRange extends OperatorToken {

  public RandomRange() {
    super(2, "RandomRange");
  }

  @Override
  public ValueToken<Double> evaluate(Environment env) {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<Double> min = checkArgumentWithSubtype(env, t1, ValueToken.class,
        Double.class.getName());
    ValueToken<Double> max = checkArgumentWithSubtype(env, t2, ValueToken.class,
        Double.class.getName());

    double rand = Math.random() * (max.VALUE - min.VALUE) + min.VALUE;
    return new ValueToken<>(rand);
  }
}