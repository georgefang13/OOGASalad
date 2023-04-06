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

    public Token evaluate(Environment env) throws IllegalArgumentException{

        ExpressionToken repeats = checkArgument(getArg(0), ExpressionToken.class, env);
        ExpressionToken exprs = checkArgument(getArg(1), ExpressionToken.class, env);

        if (repeats.size() != 2) throwError(new IllegalArgumentException("Cannot repeat with " + repeats.size() + " arguments"));

        checkArgument(repeats.get(0), VariableToken.class, env);

        VariableToken var = (VariableToken) repeats.get(0);
        Token t = repeats.get(1).evaluate(env);

        ValueToken<Double> times = checkArgumentWithSubtype(t, ValueToken.class, Double.class.getName(), env);

        int reps = (int) Math.floor(times.VALUE);

        env.createLocalScope();
        for (int i = 0; i < reps; i++){
            env.addVariable(var.NAME, new ValueToken<>((double)i));
            exprs.evaluate(env);
        }
        env.endLocalScope();
        return null;
    }
}
