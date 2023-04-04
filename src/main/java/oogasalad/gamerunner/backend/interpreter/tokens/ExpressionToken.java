package oogasalad.gamerunner.backend.interpreter.tokens;


import oogasalad.gamerunner.backend.interpreter.Environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExpressionToken extends Token implements Iterable<Token> {
  private List<Token> tokens;

  public ExpressionToken() {
    super("Expression");
    tokens = new ArrayList<>();
  }

  /**
   * pass the fill list of tokens to the expression (any previously added tokens
   * will be overwritten)
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
  public void addToken(Token t) {
    tokens.add(t);
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
  public void set(int i, Token t) {
    tokens.set(i, t);
  }

  @Override
  public Token evaluate(Environment env) {
    Token t = new ValueToken<>(0.);
    for (Token expr : tokens) {
      t = expr.evaluate(env);
    }
    return t;
  }

  @Override
  public Iterator<Token> iterator() {
    return tokens.iterator();
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