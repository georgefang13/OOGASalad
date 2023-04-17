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
public class RemoveItem extends OperatorToken {

  public RemoveItem() {
    super(2, "RemoveItem");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = checkArgument(env, getArg(0), ValueToken.class, VariableToken.class);
    t1 = t1.evaluate(env);
    Token t2 = getArg(1);
    if (t2 instanceof VariableToken) {
      t2 = t2.evaluate(env);
    }
    ValueToken<Double> dindex = checkArgumentWithSubtype(env, t1, ValueToken.class,
        Double.class.getName());
    ExpressionToken list = checkArgument(env, t2, ExpressionToken.class);

    int index = dindex.VALUE.intValue();
    list.remove(index, env);
    return null;
  }
}