package oogasalad.gamerunner.backend.interpreter.commands.operators;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.*;

/**
 * Computes if x && y
 */
public class ListContains extends OperatorToken {

    public ListContains() {
        super(2, "ListContains");
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
        return new ValueToken<>(list.index(t1, env) >= 0);
    }
}