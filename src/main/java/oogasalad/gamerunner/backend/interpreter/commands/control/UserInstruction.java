package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.VariableToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;
import oogasalad.gamerunner.backend.interpreter.tokens.ReturnToken;

/**
 * User defined instruction
 */
public class UserInstruction extends OperatorToken {

  private final ExpressionToken argTokens;
  private final ExpressionToken exprs;

  public UserInstruction(String name, ExpressionToken argTokens, ExpressionToken exprs) {
    super(argTokens.size(), name);
    this.argTokens = argTokens;
    this.exprs = exprs;
  }

  @Override
  public UserInstruction copy(){
    return null;
  }

  @Override
  public Token evaluate(Environment env) {

    ExpressionToken exprsCopy = exprs.copy();

    env.createLocalScope();
    for (int i = 0; i < argTokens.size(); i++) {
      VariableToken var = (VariableToken) argTokens.get(i);
      ValueToken<?> val = (ValueToken<?>) getArg(i).evaluate(env);
      env.addVariable(var.NAME, val);
    }
    Token t = exprsCopy.evaluate(env);
    env.endLocalScope();

    if (t instanceof ReturnToken r){
      return r.VALUE;
    }
    return t;
  }
}
