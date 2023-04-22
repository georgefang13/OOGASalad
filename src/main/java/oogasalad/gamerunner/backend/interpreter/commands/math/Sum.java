package oogasalad.gamerunner.backend.interpreter.commands.math;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes the sum of two numbers
 */
public class Sum extends OperatorToken {

  public Sum() {
    super(2, "Sum");
  }

  private Object removeDecimals(Object o) {
    if (o instanceof Double) {
      Double d = (Double) o;
      if (d == d.intValue()) {
        return d.intValue();
      }
    }
    return o;
  }

  @Override
  public Token evaluate(Environment env) {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<?> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class, Double.class.getName(),
        String.class.getName());
    ValueToken<?> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class, Double.class.getName(),
        String.class.getName());

    if (x1.VALUE instanceof String || x2.VALUE instanceof String) {
      return new ValueToken<>(
          removeDecimals(x1.VALUE).toString() + removeDecimals(x2.VALUE).toString());
    }

    return new ValueToken<>((double) x1.VALUE + (double) x2.VALUE);
  }
}