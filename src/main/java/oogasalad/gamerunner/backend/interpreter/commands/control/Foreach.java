package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Repeats the given expressions the given number of times
 */
public class Foreach extends OperatorToken {
    public Foreach() {
        super(2, "Foreach");
    }

    @Override
    public Token evaluate(Environment env) {

        ExpressionToken header = checkArgument(env, getArg(0), ExpressionToken.class);
        ExpressionToken exprs = checkArgument(env, getArg(1), ExpressionToken.class);

        if (header.size() != 2) {
            throw new IllegalArgumentException("Foreach header must have 2 arguments");
        }

        VariableToken v = checkArgument(env, header.get(0), VariableToken.class);
        Token t = header.get(1);
        if (!(t instanceof ExpressionToken)) {
            t = t.evaluate(env);
        }
        ExpressionToken list = checkArgument(env, t, ExpressionToken.class);

        Token result = null;
        for (Token item : list){
            env.addVariable(v.NAME, item);
            result = exprs.evaluate(env);
            if (result instanceof ReturnToken || result instanceof Break){
                break;
            }
        }
        if (result instanceof Break || result instanceof Continue) result = null;
        return result;
    }
}
