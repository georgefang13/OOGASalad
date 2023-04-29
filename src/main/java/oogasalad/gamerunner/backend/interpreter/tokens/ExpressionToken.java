package oogasalad.gamerunner.backend.interpreter.tokens;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.commands.control.Break;
import oogasalad.gamerunner.backend.interpreter.commands.control.Continue;
import oogasalad.gamerunner.backend.interpreter.commands.control.Return;
import oogasalad.gamerunner.backend.interpreter.commands.control.ReturnNull;

public class ExpressionToken extends Token implements Iterable<Token> {

  private List<Token> tokens;

  public ExpressionToken() {
    super("Expression");
    tokens = new ArrayList<>();
  }

  /**
   * pass the fill list of tokens to the expression (any previously added tokens will be
   * overwritten)
   *
   * @param tokens list of tokens to add to expression
   */
  public void passTokens(List<Token> tokens) {
    this.tokens = tokens;
  }

  /**
   * add a token to the expression
   *
   * @param t token to add to expression
   */
  public void addToken(Token t, Environment env) {
    tokens.add(t);
    exportSelf(env);
  }

  /**
   * get the number of tokens in the expression
   *
   * @return number of tokens in expression
   */
  public int size() {
    return tokens.size();
  }

  /**
   * get the token at index i
   *
   * @param i index of token to get
   * @return token at index i
   */
  public Token get(int i) {
    return tokens.get(i);
  }

  /**
   * set the token at index i to t
   *
   * @param i index of token to set
   * @param t token to set at index i
   */
  public void set(int i, Token t, Environment env) {
    tokens.set(i, t);
    exportSelf(env);
  }

  public void remove(int i, Environment env) {
    tokens.remove(i);
    exportSelf(env);
  }

  public int index(Token t, Environment env) {
    int index = 0;
    for (Token tok : this){
        if (tok.equals(t, env)){
            return index;
        }
        index++;
    }
    return -1;
  }

  public int length() {
    return tokens.size();
  }

  private void exportSelf(Environment env) {
    if (link != null) {
      link.set(export(env));
    }
  }

  @Override
  public Token evaluate(Environment env) {
    for (Token expr : tokens) {
      Token t = expr.evaluate(env);
      if (t instanceof ReturnToken || t instanceof Break || t instanceof Continue) {
        return t;
      }
    }
    return null;
  }

  @Override
  public Iterator<Token> iterator() {
    return tokens.iterator();
  }

  @Override
  public Object export(Environment env) {
    List<Object> list = new ArrayList<>();
    for (Token t : tokens) {
      list.add(t.export(env));
    }
    return list;
  }

  @Override
  public boolean equals(Token t, Environment env) {
    if (t instanceof ExpressionToken) {
      ExpressionToken e = (ExpressionToken) t;
      if (e.length() == length()) {
        for (int i = 0; i < length(); i++) {
          if (!get(i).equals(e.get(i), env)) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }

  @Override
  public ExpressionToken copy(){
    ExpressionToken copy = new ExpressionToken();
    List<Token> innerCopy = new ArrayList<>();
    for (Token t : tokens){
      innerCopy.add(t.copy());
    }
    copy.passTokens(innerCopy);
    return copy;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("<" + TYPE + " ");
    for (Token t : tokens) {
      s.append(t.toString()).append(" ");
    }
    return s + ">";
  }
}