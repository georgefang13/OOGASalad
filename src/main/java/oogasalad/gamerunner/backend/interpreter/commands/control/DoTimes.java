package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class DoTimes extends OperatorToken {

  public DoTimes() {
    super(2, "DoTimes");
  }

  public Token evaluate(Environment env) throws IllegalArgumentException {

    ExpressionToken repeats = checkArgument(env, getArg(0), ExpressionToken.class);
    ExpressionToken exprs = checkArgument(env, getArg(1), ExpressionToken.class);

    if (repeats.size() != 2) {
      throwError(
          new IllegalArgumentException("Cannot repeat with " + repeats.size() + " arguments"));
    }

    checkArgument(env, repeats.get(0), VariableToken.class);

    VariableToken var = (VariableToken) repeats.get(0);
    Token t = repeats.get(1).evaluate(env);

    ValueToken<Double> times = checkArgumentWithSubtype(env, t, ValueToken.class,
        Double.class.getName());

    int reps = (int) Math.floor(times.VALUE);

    Token result = null;

    env.createLocalScope();
    for (int i = 0; i < reps; i++) {
      env.addVariable(var.NAME, new ValueToken<>((double) i));
      result = exprs.evaluate(env);
      if (result instanceof ReturnToken || result instanceof Break){
        break;
      }
    }
    env.endLocalScope();

    if (result instanceof Break || result instanceof Continue) result = null;
    return result;
  }
}
