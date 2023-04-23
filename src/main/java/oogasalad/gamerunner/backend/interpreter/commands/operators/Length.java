package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;


public class Length extends OperatorToken {

  public Length() {
    super(1, "Length");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t = getArg(0).evaluate(env);
    if (!(t instanceof ExpressionToken)) {
      t = t.evaluate(env);
    }
    ExpressionToken x = checkArgument(env, t, ExpressionToken.class);
    int len = x.length();
    return new ValueToken<>((double) len);
  }
}