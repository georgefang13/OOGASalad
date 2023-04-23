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
public class SetItem extends OperatorToken {

  public SetItem() {
    super(3, "SetItem");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1);
    Token t3 = getArg(2);
    if (t2 instanceof VariableToken) {
      t2 = t2.evaluate(env);
    }
    if (!(t3 instanceof ExpressionToken)) {
      t3 = t3.evaluate(env);
    }

    ExpressionToken list = checkArgument(env, t2, ExpressionToken.class);

    ValueToken<Double> dindex = checkArgumentWithSubtype(env, t1, ValueToken.class,
        Double.class.getName());

    int index = dindex.VALUE.intValue();
    list.set(index, t3, env);

    return null;
  }
}
