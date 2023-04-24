package oogasalad.gamerunner.backend.interpreter.commands.math;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the x^y
 */
public class Power extends OperatorToken {

  public Power() {
    super(2, "Power");
  }

  @Override
  public Token evaluate(Environment env) {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<Double> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class,
        Double.class.getName());
    ValueToken<Double> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class,
        Double.class.getName());

    return new ValueToken<>(Math.pow(x1.VALUE, x2.VALUE));
  }
}
