package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;

public class VariableToken extends Token {

  public final String NAME;

  public VariableToken(String name) {
    super("Variable", name);
    NAME = name;
  }

  @Override
  public Token evaluate(Environment env) {
    return env.getLocalVariable(NAME);
  }

  @Override
  public Object export(Environment env) {
    Token t = env.getLocalVariable(NAME);
    return t.export(env);
  }

  @Override
  public VariableToken copy(){
    return new VariableToken(NAME);
  }

  @Override
  public boolean equals(Token t, Environment env){
      Token t1 = evaluate(env);
      return t.equals(t1, env);
  }

  @Override
  public String toString() {
    return "<" + TYPE + " " + NAME + ">";
  }
}
