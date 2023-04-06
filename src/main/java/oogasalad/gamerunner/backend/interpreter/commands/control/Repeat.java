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
        ExpressionToken exprs = checkArgument(getArg(1), ExpressionToken.class, env);

        Token t = getArg(0).evaluate(env);

        ValueToken<Double> repeats = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(), env);

        int reps = repeats.VALUE.intValue();

        for (int i = 0; i < reps; i++) {
            env.addVariable(":repcount", new ValueToken<>((double) i));
            exprs.evaluate(env);
        }
        return null;
    }
}
