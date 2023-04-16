package oogasalad.gamerunner.backend.interpreter;

import java.util.ArrayList;
import java.util.List;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;

public class Evaluator {

  private final Environment environment;
  private List<Token> exprs;
  private List<Token> exprsCopy;

  public Evaluator(Environment env) {
    environment = env;
  }

  public void load(List<Token> asts) {
    this.exprs = new ArrayList<>(asts);
    this.exprsCopy = new ArrayList<>(asts);
  }

  /**
   * evaluates all expressions currently loaded
   */
  public void evaluate() {
    exprsCopy = new ArrayList<>(exprs);
    while (exprsCopy.size() > 0) {
      step();
    }
  }

  /**
   * evaluates the next expression in the list of expressions currently loaded
   *
   * @return the result of the expression computation
   */
  public Token step() {
    return evaluateNextExpression(exprsCopy);
  }

  /**
   * evaluates the next expression in a list of expressions
   *
   * @param exprs the list of expressions
   * @return the result of the expression
   */
  private Token evaluateNextExpression(List<Token> exprs) {
    if (exprs.size() == 0) {
      return null;
    }

    Token pt = exprs.remove(0);
    return pt.evaluate(environment);
  }
}

