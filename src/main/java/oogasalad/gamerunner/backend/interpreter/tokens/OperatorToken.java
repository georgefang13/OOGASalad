package oogasalad.gamerunner.backend.interpreter.tokens;

import com.sun.jdi.Value;
import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.exceptions.IllegalTokenTypeException;

import java.util.Arrays;

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
   * Passes arguments to the operator. Throws an exception if the number of arguments is incorrect
   *
   * @param args array of arguments
   */
  public void passArguments(Token[] args) {
//    System.out.println("Passing arguments to " + NAME + ": " + Arrays.asList(args) );
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
   * Checks if the given token is of the given type and subtype. Throws an exception if it is not.
   *
   * @param env
   * @param t       token to check
   * @param type    type to check
   * @param subtype subtype to check - Class.getName()
   * @throws IllegalArgumentException if the token is not of the given type and subtype
   */
  protected <T> T checkArgumentWithSubtype(Environment env, Token t, Class<?> type,
      String... subtype) throws IllegalArgumentException {

    checkArgument(env, t, type);

    boolean works = false;

    for (String s : subtype) {
      try {
        checkArgumentWithSubtype(env, t, type, s);
        works = true;
        break;
      } catch (IllegalArgumentException ignored) {
      }
    }

    if (!works) {
      String s = env.getLanguageResource("argumentSubtypeError");

      String[] simpleSubtypes = new String[subtype.length];

      for (int i = 0; i < subtype.length; i++) {
        simpleSubtypes[i] = subtype[i].substring(subtype[i].lastIndexOf('.') + 1);
      }

      s = String.format(s, t, NAME, type.getSimpleName(), String.join(" or ", simpleSubtypes),
          t.getClass().getSimpleName(), t.SUBTYPE);
      throwError(new IllegalArgumentException(s));
    }

    return (T) t;
  }

  protected <T> T checkArgumentWithSubtype(Environment env, Token t, Class<?> type, String subtype)
      throws IllegalArgumentException {

    checkArgument(env, t, type);

    boolean containsSubtype = false;

    Class<?> c = null;
    try {
      c = Class.forName(subtype);
    } catch (ClassNotFoundException ignored) {
    }

    if (t instanceof ValueToken v && c != null) {
      if (c.isInstance(v.VALUE)) {
        containsSubtype = true;
      }
    } else if (t.SUBTYPE.equals(subtype)) {
      containsSubtype = true;
    }
    else if (t.SUBTYPE.equals(subtype)) containsSubtype = true;

    if (!containsSubtype) {
      String s = env.getLanguageResource("argumentSubtypeError");

      String simpleSubtype = subtype.substring(subtype.lastIndexOf('.') + 1);
      s = String.format(s, t, NAME, type.getSimpleName(), String.join(" or ", simpleSubtype),
          t.getClass().getSimpleName(), t.SUBTYPE);
      throwError(new IllegalArgumentException(s));
    }


    return (T) t;
  }



  /**
   * Checks if the given token is of the given type. Throws an exception if it is not.
   *
   * @param env
   * @param t    token to check
   * @param type type to check
   * @throws IllegalArgumentException if the token is not of the given type
   */
  protected <T> T checkArgument(Environment env, Token t, Class<?>... type)
      throws IllegalArgumentException {
    boolean hasType = false;
    for (Class<?> c : type) {
      if (t == null) {
        break;
      }
      if (c.isInstance(t)){
        hasType = true;
        break;
      }
    }

    String[] simpleNames = new String[type.length];
    for (int i = 0; i < type.length; i++) {
      simpleNames[i] = type[i].getSimpleName();
    }

    if (!hasType) {
      String s = env.getLanguageResource("argumentTypeError");
      s = String.format(s, t, NAME, String.join(" or ", simpleNames),
          t == null ? "null" : t.getClass().getSimpleName());
      throw new IllegalTokenTypeException(s);
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
  public boolean equals(Token t, Environment env) {
    Token myt = evaluate(env);
    if (!(t instanceof ExpressionToken) && t != null) {
      t = t.evaluate(env);
    }
    if (myt == null){
      if (t == null) return true;
      return t.evaluate(env) == null;
    }
    return myt.equals(t, env);
  }

  @Override
  public OperatorToken copy(){
    Class clazz = this.getClass();
    try {
      OperatorToken op = (OperatorToken) clazz.getConstructor().newInstance();
      if (args != null) {
        Token[] argsCopy = new Token[numArgs];
        for (int i = 0; i < numArgs; i++) {
          argsCopy[i] = args[i].copy();
        }
        op.passArguments(argsCopy);
      }
      return op;
    } catch (Exception e) {
//      e.printStackTrace();

    }
    return this;
  }

  @Override
  public String toString() {
    return String.format("<%s %s[%d]>", TYPE, NAME + " " + this.getClass().getSimpleName(), numArgs);
  }
}