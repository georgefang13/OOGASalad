package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

public class Token {

  public final String TYPE;
  public final String SUBTYPE;
  public final int LINE;

  protected Variable link;

  public Token(String type) {
    this(type, "");
  }

  public Token(String type, String subtype) {
    this(type, subtype, -1);
  }

  public Token(String type, String subtype, int line) {
    TYPE = type;
    SUBTYPE = subtype;
    LINE = line;
  }

  /**
   * evaluate the token
   *
   * @param env evaluator to use
   * @return evaluated token
   */
  public Token evaluate(Environment env) {
    return new ValueToken<>(0.);
  }

  public Object export(Environment env) {
    return null;
  }

  public void linkVariable(Variable v) {
    link = v;
  }

  public void unlinkVariable() {
    link = null;
  }

  public Variable getLink() {
    return link;
  }

  public Token copy(){
    return new Token(TYPE, SUBTYPE, LINE);
  }

  public boolean equals(Token t, Environment env) {
    return TYPE.equals(t.TYPE) && SUBTYPE.equals(t.SUBTYPE);
  }

  @Override
  public String toString() {
    return "<" + TYPE + ">";
  }
}
