package oogasalad.gamerunner.backend.interpreter.commands.control;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.ExpressionToken;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Repeats the given expressions the given number of times
 */
public class If extends OperatorToken {
    public If() {
        super(2, "If");
    }

    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t = getArg(0).evaluate(env);

        ValueToken<Boolean> b = checkArgumentWithSubtype(t, ValueToken.class, Boolean.class.getName(), env);
        ExpressionToken exprs =  checkArgument(getArg(1), ExpressionToken.class, env);

        if (b.VALUE) {
            exprs.evaluate(env);
        }

        return null;
    }
}
