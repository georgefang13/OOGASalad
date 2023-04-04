package oogasalad.gamerunner.backend.interpreter.tokens;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.exceptions.IllegalTokenTypeException;

abstract public class OperatorToken extends Token {
  private Token[] args;
  private final int numArgs;

  public final String NAME;

  public OperatorToken(String name) {
    this(0, name);
  }

  public OperatorToken(int numArguments, String name) {
    super("Operator", name);
    numArgs = numArguments;
    NAME = name;
  }

  /**
   * Passes arguments to the operator. Throws an exception if the number of
   * arguments is incorrect
   * 
   * @param args array of arguments
   */
  public void passArguments(Token[] args) {
    this.args = new Token[args.length];
    System.arraycopy(args, 0, this.args, 0, args.length);
  }

  /**
   * returns the number of arguments the operator takes
   * 
   * @return number of arguments the operator takes
   */
  public int getNumArgs() {
    return numArgs;
  }

  /**
   * Evaluates the operator with the given arguments
   *
   * @param env Evaluator object
   * @return result of the operation
   */
  @Override
  public abstract Token evaluate(Environment env) throws IllegalArgumentException;

  protected void throwError(RuntimeException e) {
    throw e;
  }

  /**
   * Checks if the given token is of the given type and subtype. Throws an
   * exception if it is not.
   * 
   * @param t        token to check
   * @param type     type to check
   * @param subtype  subtype to check - Class.getName()
   * @param errorMsg error message to throw if the token is not of the given type
   *                 and subtype
   * @throws IllegalArgumentException if the token is not of the given type and
   *                                  subtype
   */
  protected <T> T checkArgumentWithSubtype(Token t, Class<?> type, String subtype, String errorMsg)
      throws IllegalArgumentException {
    if (t.getClass().equals(type)) {
      if (!t.SUBTYPE.equals(subtype)) {
        throwError(new IllegalArgumentException(errorMsg));
      }
    } else {
      throwError(new IllegalTokenTypeException(errorMsg));
    }
    return (T) t;
  }

  /**
   * Checks if the given token is of the given type. Throws an exception if it is
   * not.
   * 
   * @param t        token to check
   * @param type     type to check
   * @param errorMsg error message to throw if the token is not of the given type
   * @throws IllegalArgumentException if the token is not of the given type
   */
  protected <T> T checkArgument(Token t, Class<?> type, String errorMsg) throws IllegalArgumentException {
    if (t == null || !t.getClass().equals(type)) {
      throw new IllegalTokenTypeException(errorMsg);
    }
    return (T) t;
  }

  /**
   * Returns the argument at the given index
   *
   * @param index index of the argument
   * @return argument at the given index
   */
  protected Token getArg(int index) {
    return args[index];
  }

  @Override
  public String toString() {
    return String.format("<%s %s[%d]>", TYPE, NAME, numArgs);
  }
}