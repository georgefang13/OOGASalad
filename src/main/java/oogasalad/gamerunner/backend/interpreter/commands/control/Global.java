package oogasalad.gamerunner.backend.interpreter.commands.control;


import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * makes the use of a variable global within local scope
 */
public class Global extends OperatorToken {

  public Global() {
    super(1, "Global");
  }

  @Override
  public Token evaluate(Environment env) {

    Token t = getArg(0);

    VariableToken x = checkArgument(env, t, VariableToken.class);

    env.addVariable(x.NAME + "-global", t);
    return null;
  }
}