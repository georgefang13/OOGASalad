package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes if !x
 */
public class Not extends OperatorToken {

  public Not() {
    super(1, "Not");
  }

  @Override
  public Token evaluate(Environment env) {
    Token t = getArg(0).evaluate(env);

    ValueToken<Boolean> x1 = checkArgumentWithSubtype(env, t, ValueToken.class,
        Boolean.class.getName());

    return new ValueToken<>(!x1.VALUE);
  }
}
