package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;

public class ValueToken<T> extends Token {

  public final T VALUE;

  public ValueToken(T value) {
    super("Value", value.getClass().getName());
    VALUE = value;
  }

  @Override
  public Token evaluate(Environment env) {
    return this;
  }

  @Override
  public Object export(Environment env) {
    return VALUE;
  }

  @Override
  public ValueToken<T> copy(){
    return new ValueToken<>(VALUE);
  }

    @Override
    public boolean equals(Token t, Environment env){
        if (t instanceof OperatorToken o) {
            t = o.evaluate(env);
        }
        else if (t instanceof VariableToken) {
            t = t.evaluate(env);
        }

        if(t instanceof ValueToken other){
            return VALUE.equals(other.VALUE);
        }
        return false;
    }

  @Override
  public String toString() {
    return "<" + TYPE + " " + VALUE + ">";
  }
}
