package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * Repeats the given expressions the given number of times
 */
public class Del extends OperatorToken {

  public Del() {
    super(1, "Del");
  }

  public Token evaluate(Environment env) throws IllegalArgumentException {
    VariableToken var = checkArgument(env, getArg(0), VariableToken.class);
    env.removeVariable(var);
    return null;
  }
}
