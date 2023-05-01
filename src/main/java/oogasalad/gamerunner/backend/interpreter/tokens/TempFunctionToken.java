package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.commands.control.UserInstruction;

public class TempFunctionToken extends OperatorToken {

  public final String NAME;

  public TempFunctionToken(int numArgs, String name) {
    super(numArgs, name);
    NAME = name;
  }

  @Override
  public Token evaluate(Environment env) throws IllegalArgumentException {
    VariableToken var = new VariableToken(NAME);
    UserInstruction instruction = (UserInstruction) var.evaluate(env);
    Token[] args = new Token[getNumArgs()];
    for (int i = 0; i < getNumArgs(); i++) {
      args[i] = getArg(i);
    }
    instruction.passArguments(args);
    return instruction.evaluate(env);
  }

  @Override
  public TempFunctionToken copy(){
    TempFunctionToken t = new TempFunctionToken(getNumArgs(), NAME);
    Token[] args = new Token[getNumArgs()];
    for (int i = 0; i < getNumArgs(); i++) {
      args[i] = getArg(i).copy();
    }
    t.passArguments(args);
    return t;
  }
}