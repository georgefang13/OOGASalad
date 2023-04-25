package oogasalad.gamerunner.backend.interpreter.tokens;

import java.util.ArrayList;
import java.util.List;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.exceptions.CannotBeEvaluatedException;
import oogasalad.gamerunner.backend.interpreter.exceptions.InvalidSyntaxException;

public class SetToken extends Token {

  private List<Token> tokens;
  private OperatorToken headOperator;

  public SetToken(OperatorToken head) {
    super("Set");
    tokens = new ArrayList<>();
    headOperator = head;
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
   * @param i index of token to get
   * @return token at index i
   */
  public Token get(int i) {
    return tokens.get(i);
  }

  @Override
  public Token evaluate(Environment env) {
    Token rollover = null;
    int i = 0;
    int numArgs = headOperator.getNumArgs();
    Token[] args = new Token[numArgs];
    while (i < tokens.size()) {
      // if the function returns a value, use that value as the first argument
      if (rollover == null) {
        args[0] = tokens.get(i++);
      } else {
        args[0] = rollover;
        // if there would be an infinite loop (returns an argument, takes one argument),
        // throw an exception
        if (numArgs == 1) {
          throw new InvalidSyntaxException(
              "Set called with operator " + headOperator + " that takes less than 2 arguments");
        }
      }

      if (i + numArgs - 1 > tokens.size()) {
        throw new CannotBeEvaluatedException("Not enough arguments for " + headOperator);
      }

      // fill in the rest of the arguments
      for (int j = 1; j < numArgs; j++) {
        args[j] = tokens.get(i++);
      }

      headOperator.passArguments(args);
      rollover = headOperator.evaluate(env);
    }
    return rollover;
  }

  @Override
  public SetToken copy(){
    SetToken copy = new SetToken(headOperator.copy());
    List<Token> tokenCopies = new ArrayList<>();
    for (Token t : tokens){
      tokenCopies.add(t.copy());
    }
    copy.passTokens(tokenCopies);
    return copy;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("(" + TYPE + " " + headOperator.toString() + " ");
    for (Token t : tokens) {
      s.append(t.toString()).append(" ");
    }
    return s + ")";
  }
}