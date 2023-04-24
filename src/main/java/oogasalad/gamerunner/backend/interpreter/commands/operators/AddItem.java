package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * Computes if x && y
 */
public class AddItem extends OperatorToken {

  public AddItem() {
    super(2, "AddItem");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = getArg(0);
    if (!(t1 instanceof ExpressionToken)){
        t1 = t1.evaluate(env);
    }

    Token t2 = getArg(1);
    if (t2 instanceof VariableToken) {
      t2 = t2.evaluate(env);
    }
    ExpressionToken list = checkArgument(env, t2, ExpressionToken.class);
    list.addToken(t1, env);
    return null;
  }
}