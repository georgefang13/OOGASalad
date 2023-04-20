package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes if x && y
 */
public class And extends OperatorToken {

  public And() {
    super(2, "And");
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    Token t1 = getArg(0).evaluate(env);
    Token t2 = getArg(1).evaluate(env);

    ValueToken<Boolean> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class,
        Boolean.class.getName());
    ValueToken<Boolean> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class,
        Boolean.class.getName());

    System.out.println("And " + x1.VALUE + " " + x2.VALUE + " = " + (x1.VALUE && x2.VALUE));

    return new ValueToken<>(x1.VALUE && x2.VALUE);
  }
}
