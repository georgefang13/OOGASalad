package oogasalad.gamerunner.backend.interpreter.commands.math;

import oogasalad.gamerunner.backend.interpreter.Environment;
import oogasalad.gamerunner.backend.interpreter.tokens.OperatorToken;
import oogasalad.gamerunner.backend.interpreter.tokens.Token;
import oogasalad.gamerunner.backend.interpreter.tokens.ValueToken;

/**
 * Computes if x && y
 */
public class And extends OperatorToken {

    public And(){
        super(2, "And");
    }

    @Override
    public Token evaluate(Environment env) throws IllegalArgumentException{
        Token t1 = getArg(0).evaluate(env);
        ValueToken<Boolean> x1 = checkArgumentWithSubtype(env, t1, ValueToken.class, Boolean.class.getName());

        if (!x1.VALUE) return new ValueToken<>(false);

        Token t2 = getArg(1).evaluate(env);
        ValueToken<Boolean> x2 = checkArgumentWithSubtype(env, t2, ValueToken.class, Boolean.class.getName());

        return new ValueToken<>(x2.VALUE);
    }
}
