package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class Repeat extends OperatorToken {

  public Repeat() {
    super(2, "Repeat");
  }

  @Override
  public Token evaluate(Environment env) {
    ExpressionToken exprs = checkArgument(env, getArg(1), ExpressionToken.class);

    Token t = getArg(0).evaluate(env);

    ValueToken<Double> repeats = checkArgumentWithSubtype(env, t, ValueToken.class,
        Double.class.getName());

    int reps = repeats.VALUE.intValue();
    Token ret = null;
    for (int i = 0; i < reps; i++) {
      env.addVariable(":repcount", new ValueToken<>((double) i));
      ret = exprs.evaluate(env);
      if (ret instanceof ReturnToken || ret instanceof Break) {
        break;
      }
    }
    if (ret instanceof Break || ret instanceof Continue) ret = null;
    return ret;
  }
}
