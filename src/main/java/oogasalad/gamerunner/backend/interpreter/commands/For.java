package oogasalad.gamerunner.backend.interpreter.commands;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class For extends OperatorToken {
    public For() {
        super(2, "For");
    }


    // returns [var, start, stop, increment]
    private Token[] repeatArgs(Environment env) throws IllegalArgumentException{
        checkArgument(getArg(0), ExpressionToken.class, "Cannot repeat with non-expression " + getArg(0));
        ExpressionToken repeats = (ExpressionToken) getArg(0);

        if (repeats.size() < 3 || repeats.size() > 4){
            throw new IllegalArgumentException("Cannot repeat with " + repeats.size() + " arguments");
        }

        Token[] ret = new Token[repeats.size()];

        checkArgument(repeats.get(0), VariableToken.class, "Cannot loop with non-variable " + repeats.get(0));

        Token tstart = repeats.get(1).evaluate(env);
        Token tstop = repeats.get(2).evaluate(env);

        checkArgumentWithSubtype(tstart, ValueToken.class, Double.class.getName(),
                "Cannot repeat non-number of times from " + repeats.get(1) + " = " + tstart);

        checkArgumentWithSubtype(tstop, ValueToken.class, Double.class.getName(),
                "Cannot repeat non-number of times to " + repeats.get(2) + " = " + tstop);

        ret[0] = repeats.get(0);
        ret[1] = tstart;
        ret[2] = tstop;

        if (repeats.size() == 4){
            Token tinc = repeats.get(3).evaluate(env);

            checkArgumentWithSubtype(tinc, ValueToken.class, Double.class.getName(),
                    "Cannot repeat non-number of times by " + repeats.get(3) + " = " + tinc);

            ret[3] = tinc;
        }

        return ret;
    }

    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token[] repArgs = repeatArgs(env);

        VariableToken var = (VariableToken) repArgs[0];

        double start = ((ValueToken<Double>) repArgs[1]).VALUE;
        double stop = ((ValueToken<Double>) repArgs[2]).VALUE;

        double increment = 1;

        // repeat can have an optional increment as a 4th argument (default 1)
        if (repArgs.length == 4){
            increment = ((ValueToken<Double>) repArgs[3]).VALUE;
        }
        ExpressionToken exprs = (ExpressionToken) getArg(1);

        if (start == stop || start > stop && increment > 0 || start < stop && increment < 0){
            throwError(new RuntimeException("Cannot repeat from " + start + " to " + stop + " by " + increment));
        }

        env.createLocalScope();

        for (double i = start; i < stop; i += increment){
            env.addVariable(var.NAME, new ValueToken<>(i));
            exprs.evaluate(env);
        }

        env.endLocalScope();
        return null;
    }
}
