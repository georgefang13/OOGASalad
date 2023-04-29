package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * Computes if x && y
 */
public class Item extends OperatorToken {

  public Item() {
    super(2, "Item");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = getArg(0).evaluate(env);

    Token t2 = getArg(1);
    if (! (t2 instanceof ExpressionToken)) {
      t2 = t2.evaluate(env);
    }

    ExpressionToken x2 = checkArgument(env, t2, ExpressionToken.class);

    ValueToken<Double> d = checkArgumentWithSubtype(env, t1, ValueToken.class,
        Double.class.getName());

    int index = d.VALUE.intValue();

    if (x2.size() <= index) {
      throw new IllegalArgumentException(
          "Cannot get index " + index + " from list of size " + x2.size());
    }

    Token t = x2.get(index);

    if (!(t instanceof ExpressionToken)) {
      t = t.evaluate(env);
    }

    return t;
  }
}
