package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes if x && y
 */
public class ListIndex extends OperatorToken {

    public ListIndex() {
        super(2, "ListIndex");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException {
        Token t1 = getArg(0);
        if (t1 instanceof VariableToken) {
            t1 = t1.evaluate(env);
        }
        Token t2 = getArg(1);
        if (t2 instanceof VariableToken) {
            t2 = t2.evaluate(env);
        }
        ExpressionToken list = checkArgument(env, t2, ExpressionToken.class);
        return new ValueToken<>((double) list.index(t1, env));
    }
}