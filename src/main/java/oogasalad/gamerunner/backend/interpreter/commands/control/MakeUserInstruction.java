package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.CommandToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;

/**
 * Creates a new function with the given name, arguments, and expressions
 */
public class MakeUserInstruction extends OperatorToken {

  public MakeUserInstruction() {
    super(3, "MakeUserInstruction");
  }

  public Token evaluate(Environment env) {

    CommandToken name = checkArgument(env, getArg(0), CommandToken.class);
    ExpressionToken argTokens = checkArgument(env, getArg(1), ExpressionToken.class);
    ExpressionToken exprs = checkArgument(env, getArg(2), ExpressionToken.class);

    for (int i = 0; i < argTokens.size(); i++) {
      checkArgument(env, argTokens.get(i), VariableToken.class);
    }

    UserInstruction instruction = new UserInstruction(name.NAME, argTokens, exprs);
    env.addVariable(name.NAME, instruction);
    return null;
  }
}
