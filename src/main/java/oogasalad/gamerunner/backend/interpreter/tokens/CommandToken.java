package oogasalad.gamerunner.backend.interpreter.tokens;


import oogasalad.gamerunner.backend.interpreter.Environment;

public class CommandToken extends Token {

  public final String NAME;

  public CommandToken(String name) {
    super("Command", name);
    NAME = name;
  }

  @Override
  public Token evaluate(Environment env) {
    return env.getLocalVariable(NAME);
  }

  @Override
  public CommandToken copy(){
    return new CommandToken(NAME);
  }

  @Override
  public String toString(){
    return "<Command " + NAME + ">";
  }
}
